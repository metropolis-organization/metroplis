package com.metropolis.common.encrypt;

import com.metropolis.common.encrypt.exception.UnFoundAccessKeyException;
import com.metropolis.common.encrypt.exception.UnFoundAppIDException;
import com.metropolis.common.redis.serialize.HessianRedisSerializer;
import org.springframework.util.StringUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 *
 * @description: AES 对称加密
 * @author: Pop
 * @create: 2019-09-19 16:04
 **/
public class AECProcessor {

    private static final String CRY_NAME = "AES";
    private static final Charset UTF_8 = Charset.forName("utf-8");
    private static final int SAFE_LEVEL_128= 128;// 128 192 256
    private static final int SAFE_LEVEL_192= 192;// 128 192 256
    private static final int SAFE_LEVEL_256= 256;

    private static HessianRedisSerializer serializer = new HessianRedisSerializer();

    public static String serialize2String(Object o){ return Base64.getEncoder().encodeToString(serialize(o)); }
    public static Object string2Deserialize(String s){ return deserialize(Base64.getDecoder().decode(s)); }
    public static byte[] serialize(Object o){ return serializer.serialize(o); }
    public static Object deserialize(byte[] bytes){return serializer.deserialize(bytes);}

    //全局超时时间设置
    /**
     * 标志位 #
     * 标志位后一位表示选择了时间的单位
     * S:秒
     * M:分
     * H:时
     * 后面为具体时间，例如这里的全局token超时时间被设置为三十秒后过期
     */
    public static final String TIMEOUT_GLOBAL = "#S300";

    //专门用于解码token的密匙
    public static  SecretKey TIME_KEY;

    static {
        try {
            TIME_KEY = getAESkey(SAFE_LEVEL_128);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得一个密匙长度为192的AES 密匙
     * @return 经过Base64处理后的密匙字符串
     * @throws NoSuchAlgorithmException
     */
    public static String getStrKeyAES64(int safeLevel) throws NoSuchAlgorithmException {
        return Base64.getEncoder().encodeToString(getAESkey(safeLevel).getEncoded());
    }

    /**
     * @return 未经过任何处理的密匙
     * @param safeLevel 加密级别
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getStrKeyAES(int safeLevel) throws NoSuchAlgorithmException {
        return new String(getAESkey(safeLevel).getEncoded());
    }
    public static SecretKey getAESkey(int safeLevel) throws NoSuchAlgorithmException {
        KeyGenerator keGen = KeyGenerator.getInstance(CRY_NAME);
        SecureRandom secureRandom = new SecureRandom(String.valueOf(System.currentTimeMillis()).getBytes(UTF_8));
        keGen.init(safeLevel,secureRandom);
        return keGen.generateKey();
    }
    /**
     * 将字符串类型的srcreKey转换为SecretKey
     * @param key
     * @return
     */
    public static SecretKey strKey2SecretKey(String key){
        return new SecretKeySpec(key.getBytes(UTF_8),CRY_NAME);
    }

    public static SecretKey strKey2SecretKey64(String key){
        byte[] bytes = Base64.getDecoder().decode(key);
        SecretKeySpec secretKey = new SecretKeySpec(bytes,CRY_NAME);
        return secretKey;
    }


    /**
     * 加密
     * @param content 等待加密的内容
     * @param secretKey 加密使用的AES密匙
     * @return 加密后的密文byte[]
     */
    public static byte[] encrypt(byte[] content,SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher =  Cipher.getInstance(CRY_NAME);
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);
        return cipher.doFinal(content);
    }

    public static String encrypt64(String content,SecretKey secretKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return Base64.getEncoder().encodeToString(encrypt(content.getBytes(),secretKey));
    }

    public static String encrypt64(String content,String accessKey) throws InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
        return encrypt64(content,strKey2SecretKey64(accessKey));
    }

    /**
     * 处理由base64加密过的字符串，在通过aes解密，获得字符串
     * @param src
     * @param secretKey
     * @return
     */
    public static String decrypt64(String src,SecretKey secretKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        byte[] srcb = decrypt(Base64.getDecoder().decode(src),secretKey);
        return new String(srcb);
    }

    public static byte[] decrypt(byte[] content,SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher =  Cipher.getInstance(CRY_NAME);
        cipher.init(Cipher.DECRYPT_MODE,secretKey);
        return cipher.doFinal(content);
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
     * 生成签名
     * @param code code
     * @param accessKey
     * @return
     */
    public static String signature(Code code,String accessKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        String appId = code.getAppId();
        if(StringUtils.isEmpty(appId)){throw  new UnFoundAppIDException(); }
        if(StringUtils.isEmpty(accessKey)){throw  new UnFoundAccessKeyException(); }
        SecretKey key = AECProcessor.strKey2SecretKey(accessKey);
        return Base64.getEncoder().encodeToString(AECProcessor.encrypt(code.toString().getBytes(),key));
    }


    /**
     * 生成token，超时时间默认为全局时间设置
     * @return 数据和日期一起加密传输
     */
    public static String token() throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        StringBuilder result = new StringBuilder(String.valueOf(System.currentTimeMillis()));
        result.append(TIMEOUT_GLOBAL);
        return Base64.getEncoder().encodeToString(encrypt(result.toString().getBytes(),TIME_KEY));
    }

    /**
     * 设置这个token的超时时间
     * @param unit
     * @param value
     * @return
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public static String token(TokenTime unit,int value) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        StringBuilder result = new StringBuilder(String.valueOf(System.currentTimeMillis()));
        result.append(unit.timestamp(value));
        return Base64.getEncoder().encodeToString(encrypt(result.toString().getBytes(),TIME_KEY));
    }

    /**
     * 使用token获得一个时间
     * @param token 这里的token应该是去掉了时间戳
     * @return
     */
    public static Date parseToken2Date(String token) {
        return new Date(Long.valueOf(token));
    }

    /**
     * 验证token是否过期
     * @return true 为还未过期 false 已经过期
     */
    public static boolean checkToken(String token){
        TimeDto dto=parseToken(token);
        if(Objects.isNull(dto)){return false;}
        return dto.getCurrDate().after(dto.getTokenDate());
    }

    private static TimeDto parseToken(String token){
        TimeDto timeDto = new TimeDto();
        timeDto.setCurrDate(new Date(System.currentTimeMillis()));
        Date tokenDate = TokenTime.getTime(token);
        if(Objects.isNull(tokenDate)){return null;}
        timeDto.setTokenDate(tokenDate);
        return timeDto;
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

    public static void main(String[] args) throws Exception {

        //reigster
//        UUID appId = UUID.randomUUID();
//        String key=AECProcessor.getStrKeyAES64(SAFE_LEVEL_192);
//        System.out.println(key);
////        // todo 入库
////
////
//        String accessKey = getStrKeyAES64(SAFE_LEVEL_192);
////        String accessKey = "hlWgmjyIbWRksk8UKP0iOa5qEs3aggF5";
//        System.out.println(accessKey);
//
//        String usernamepassword = "up";
////        String ens = encrypt64(s,accessKey);//加密
//
//        //appid username password  增加签名
//        String appId = "zr-test";
//        Code code = new Code("zr-test","username","password");
//        String signature = AECProcessor.signature(code,accessKey);

        //通过写个方法，将这四个参数 appid username password 签名发送这边 login(code)
//===============================================================================================
        //一段传输后 服务端收到了这四个参数 验证签名是否正确


//        if(checkSignature(signature, code, new AppDao() {
//            @Override
//            public String getAccessKeyByAppId(String appId) {
//
//                return "hlWgmjyIbWRksk8UKP0iOa5qEs3aggF5";
//            }
//        })){
//            System.out.println("验证通过，发放Token");
//            // todo login operation
////            String token = token();//token的生产方式
////            token(TokenTime.SECOND,60);
//        }else{
//            System.out.println("验证失败，拒绝登陆");
//        }




//        String key = "KMH5IMD9JUE0NBE3MNY6==";
//        SecretKey secretKey= getAESkey(SAFE_LEVEL_192);
//        System.out.println(new String(secretKey.getEncoded()));
//        byte[] encode = AECProcessor.encrypt("ABC".getBytes(),
//                secretKey);
//        System.out.println("编码后: "+new String(encode));
////
//        byte[] decode = AECProcessor.decrypt(encode,secretKey);
//        System.out.println("解码后: "+new String(decode));
//        String token = token(TokenTime.MINUTE,1);
////        String token = "HzZIPwS5Ire32q+k3S6UuA==";
////        System.out.println(token);
//        String oldToken = new String(decrypt(Base64.getDecoder().decode(token),TIME_KEY));
//        if(checkToken(token)){
//            System.out.println("token已经超时");
//
//        }else{
//            System.out.println("token还没有超时");
//            try {
//                TimeUnit.SECONDS.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//        System.out.println(oldToken);
//        Date old=new java.util.Date(Long.valueOf(oldToken));
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(old);
//        calendar.add(Calendar.SECOND,20);
//        Date isOver = calendar.getTime();

        String token = AECProcessor.token(TokenTime.SECOND,60);
        System.out.println(token);
//=================================================================================
        Thread thread = new Thread(()->{

            while (true){

                if(checkToken(token)){
                    System.out.println("token已经超时");
                    break;
                }else{
                    System.out.println("token还没有超时");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

            }

        });
        thread.start();
        thread.join();

    }

}
