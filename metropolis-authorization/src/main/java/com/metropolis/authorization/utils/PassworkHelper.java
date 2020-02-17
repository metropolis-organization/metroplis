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

    public static boolean checkPassword(SysUser user,String password){
        String checkPassword = new SimpleHash(
                ALGORITHM_NAME,
                password,
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                HASH_ITERATIONS).toHex();
        return !checkPassword.equals(user.getPassword());
    }

    public static SysUser encryptPassword(SysUser user) {
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        String newPassword = new SimpleHash(
                ALGORITHM_NAME,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                HASH_ITERATIONS).toHex();
        user.setPassword(newPassword);
        return user;
    }

}
