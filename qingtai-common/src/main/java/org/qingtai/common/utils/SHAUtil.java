package org.qingtai.common.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * org.qingtai.common.utils
 * Created on 2017/10/20
 *
 * @author Lichaojie
 */
public class SHAUtil {
    /**
     * 单项散列函数SHA 用于对用户密码进行加密
     * @param password
     * @return
     */
    public static String sha(String password) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] ret = messageDigest.digest(password.getBytes());
        return Base64.encodeBase64String(ret);
    }
}
