package com.metropolis.authorization.utils;

import com.metropolis.authorization.dal.entitys.SysUser;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author Pop
 * @date 2020/2/16 20:15
 */
public class PassworkHelper {
    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private static final String ALGORITHM_NAME = "md5";
    private static final int HASH_ITERATIONS = 1;

    public static boolean checkPassword(SysUser user, String password){
        return !password(user,password).equals(user.getPassword());
    }

    private static String password(SysUser user){
        return  new SimpleHash(
                ALGORITHM_NAME,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                HASH_ITERATIONS).toHex();
    }

    private static String password(SysUser user,String checkPassword){
        return  new SimpleHash(
                ALGORITHM_NAME,
                checkPassword,
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                HASH_ITERATIONS).toHex();
    }

    public static SysUser encryptPassword(SysUser user) {
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        user.setPassword(password(user));
        return user;
    }

    public static void main(String[] args) {
        String s = "ff771e5b962170c0c24428ba0e7de4f33a";
        SysUser user = new SysUser();
        user.setCredentialsSalt(s);
        user.setPassword("123456");
        System.out.println(password(user));
    }

}
