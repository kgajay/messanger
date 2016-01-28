package com.ajay.messenger.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SHAHashUtil {

    private static final Logger logger = LoggerFactory.getLogger(SHAHashUtil.class);


    public static String getSHA256Hash(String input) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(input.getBytes("UTF-8"));
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            logger.info("Generated SHAHASH : {}", sb.toString());
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error("SHAHash MessageDigest not initialized {}", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("SHAHash Unsupported Encoding {}", e);
        }
        return null;
    }

}