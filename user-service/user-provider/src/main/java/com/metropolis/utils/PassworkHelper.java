package com.metropolis.utils;

import com.metropolis.user.entity.SysUser;
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
        return !password(user).equals(user.getPassword());
    }

    private static String password(SysUser user){
       return  new SimpleHash(
               ALGORITHM_NAME,
               user.getPassword(),
               ByteSource.Util.bytes(user.getCredentialsSalt()),
               HASH_ITERATIONS).toHex();
    }

    public static SysUser encryptPassword(SysUser user) {
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        user.setPassword(password(user));
        return user;
    }

}
