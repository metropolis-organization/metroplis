package com.metropolis.common.encrypt;


import com.metropolis.common.encrypt.exception.UnFoundAccessKeyException;
import com.metropolis.common.encrypt.exception.UnFoundAppIDException;
import org.springframework.util.StringUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

/**
 * Aec 工具类
 */
public class Aec {


    private Aec(boolean isDecode, AecSafeLevel level){
        try {
            keGen = KeyGenerator.getInstance(CRY_NAME);
            this.safeLevel = level;
            coder=isDecode?new Decoder():new Encoder();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private Aec() { }

    enum AecSafeLevel{
        _128(128),_192(192),_256(256);
        private int level;
        AecSafeLevel(int level) {
            this.level=level;
        }

        public int getLevel() {
            return level;
        }
    }


    /*    解码 编码Aec对象    */

    private static Aec encoder = new Aec(false, AecSafeLevel._192);
    private static Aec decoder = new Aec(true, AecSafeLevel._192);

    private static Aec timeCoder = new Aec(true, AecSafeLevel._128);
    //专门用于解码token的密匙
    public static  SecretKey TIME_KEY;

    private static final String CRY_NAME = "AES";
    private static final Charset UTF_8 = Charset.forName("utf-8");

    //全局超时时间设置
    /**
     * 标志位 #
     * 标志位后一位表示选择了时间的单位
     * S:秒
     * M:分
     * H:时
     * 后面为具体时间，例如这里的全局token超时时间被设置为300秒后过期
     */
    private static final String TIMEOUT_GLOBAL = "#S300";

    private Coder coder = null;

    private static KeyGenerator keGen;

    static {
        try {
            keGen = KeyGenerator.getInstance(CRY_NAME);
            TIME_KEY = timeCoder.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    ;
    private  AecSafeLevel safeLevel = AecSafeLevel._128;
    private static SecureRandom secureRandom = null;

    public static Encoder getEncoder(){return (Encoder) encoder.coder;}
    public static Decoder getDecoder(){return (Decoder) decoder.coder;}

    /**
     * 返回一个由于Base64加密过后的密匙
     * @return 返回一个字符串
     */
    public static String generateKey64(){ return Base64.getEncoder().encodeToString(generateKey().getEncoded()); }

    /**
     * 生成一个密匙
     * @return
     */
    public static SecretKey generateKey(){
        secureRandom = new SecureRandom(String.valueOf(System.currentTimeMillis()).getBytes(UTF_8));
        keGen.init(AecSafeLevel._192.getLevel(),secureRandom);
        return keGen.generateKey();
    }

    /**
     * 生成token，超时时间默认为全局时间设置
     * @return 数据和日期一起加密传输
     */
    public static String token() throws Exception {
        StringBuilder result = new StringBuilder(String.valueOf(System.currentTimeMillis()));
        result.append(TIMEOUT_GLOBAL);
        return Base64.getEncoder().encodeToString((encrypt(result.toString().getBytes(),TIME_KEY)));
    }

    /**
     * 设置这个token的超时时间
     * @param unit 时分秒
     * @param value 具体的超时的值
     * @return
     * @throws Exception
     */
    public static String token(TokenTime unit, int value) throws Exception {
        StringBuilder result = new StringBuilder(String.valueOf(System.currentTimeMillis())).append(unit.timestamp(value));
        return Base64.getEncoder().encodeToString(encrypt(result.toString().getBytes(),TIME_KEY));
    }

    /**
     * 验证token是否过期
     * @return true 为还未过期 false 已经过期
     */
    public static boolean checkToken(String token){
        TimeDto dto=parseToken(token);
        return dto.getTokenDate().after(dto.getCurrDate());
    }

    private static TimeDto parseToken(String token){
        TimeDto timeDto = new TimeDto();
        timeDto.setCurrDate(new Date(System.currentTimeMillis()));
        timeDto.setTokenDate(TokenTime.getTime(token));
        return timeDto;
    }

    /**
     * 生成签名
     * @param code code
     * @param accessKey
     * @return
     */
    public static String signature(Code code, String accessKey) throws Exception {
        String appId = code.getAppId();
        if(StringUtils.isEmpty(appId)){throw  new UnFoundAppIDException(); }
        if(StringUtils.isEmpty(accessKey)){throw  new UnFoundAccessKeyException(); }
        SecretKey key = Coder.convertStr2SecretKey64(accessKey);
        return Base64.getEncoder().encodeToString(encrypt(code.toString().getBytes(),key));
    }

    /**
     * 验证签名是否正确
     * @param signature 穿过来的签名
     * @param code 包装了 appid username password的对象
     * @param dao 用来查询数据库的对象，这个需要自己实现，返回accessKey
     * @return
     * @throws Exception
     */
    public static boolean checkSignature(String signature, Code code, AppDao dao) {
        String appId = code.getAppId();
        try {
            return signature.equals(signature(code,dao.getAccessKeyByAppId(appId)));
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 加密
     * @param content 等待加密的内容
     * @param secretKey 加密使用的AES密匙
     * @return 加密后的密文byte[]
     */
    private static byte[] encrypt(byte[] content,SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher =  Cipher.getInstance(CRY_NAME);
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);
        return cipher.doFinal(content);
    }

    private static byte[] decrypt(byte[] content,SecretKey secretKey) throws Exception {
        Cipher cipher =  Cipher.getInstance(CRY_NAME);
        cipher.init(Cipher.DECRYPT_MODE,secretKey);
        return cipher.doFinal(content);
    }



    static class TimeDto{
        private Date tokenDate;
        private Date currDate;

        public Date getTokenDate() {
            return tokenDate;
        }

        public void setTokenDate(Date tokenDate) {
            this.tokenDate = tokenDate;
        }

        public Date getCurrDate() {
            return currDate;
        }

        public void setCurrDate(Date currDate) {
            this.currDate = currDate;
        }
    }

    private static class Coder{
        /**
         *
         * @param key 由于Base64 加密后的 密匙 转化成 SecretKey
         * @return
         */
        public static SecretKey convertStr2SecretKey64(String key){ return new SecretKeySpec(Base64.getDecoder().decode(key),CRY_NAME);}

    }

    public class Decoder extends Coder{

        private Decoder() { }

        public byte[] decode(byte[] content, SecretKey key) throws Exception { return Aec.decrypt(content,key); }

        public byte[] decode(byte[] content,String key) throws Exception{ return decode(content,convertStr2SecretKey64(key)); }

        public String decode(String content,SecretKey key) throws Exception{ return new String(decode(Base64.getDecoder().decode(content),key)); }

        public String decode(String content,String key) throws Exception{ return new String(decode(Base64.getDecoder().decode(content),key)); }


    }

    public class Encoder extends Coder{
        private Encoder() { }

        public byte[] encode(byte[] content, SecretKey key) throws Exception { return Aec.encrypt(content,key); }

        public byte[] encode(byte[] content,String key) throws Exception{ return encode(content,convertStr2SecretKey64(key)); }

        public String encode(String content,String key) throws Exception{ return Base64.getEncoder().encodeToString(encode(content.getBytes(),key)); }
    }
}
