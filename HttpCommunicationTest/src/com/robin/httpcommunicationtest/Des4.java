package com.robin.httpcommunicationtest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

public class Des4 {  
    // √‹‘ø  
    private final static String secretKey = "123456784";  
    private static byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0};  
  
    public static String encode(String plainText) throws Exception {  
        IvParameterSpec zeroIv = new IvParameterSpec(iv);  
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "DES");  
        Cipher cipher = Cipher.getInstance("DES");  
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);  
        byte[] encryptedData = cipher.doFinal(plainText.getBytes());  
        return Base64.encodeToString(encryptedData, 1);  
    }  
  
    public static String decode(String encryptText) throws Exception {  
        IvParameterSpec zeroIv = new IvParameterSpec(iv);  
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "DES");  
        Cipher cipher = Cipher.getInstance("DES");  
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);  
        byte[] decryptData = cipher.doFinal(Base64.decode(encryptText, 1));  
        return new String(decryptData);  
    }
    public static String mytest(String tststr)
    {
    	return tststr;
    }
}  
