package org.qingtai.common.utils;

import org.apache.commons.codec.binary.Base64;

/**
 * org.qingtai.common.utils
 * Created on 2017/10/20
 *
 * @author Lichaojie
 */
public class Base64Util {


    public static String encode(String raw) {
        Base64 base64 = new Base64();
        return base64.encodeAsString(raw.getBytes());
    }

    public static String decode(String codedStr) {
        byte[] bytes = Base64.decodeBase64(codedStr.getBytes());
        return new String(bytes);
    }

    public static void main(String[] args){
        String secret = Base64Util.encode("");
        String source = Base64Util.decode(secret);

        System.out.println(secret + "\n" + source);
    }
}
