package com.example.digitalsignatureAESRSA.RSA;

import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;

import java.security.SecureRandom;
import java.security.Signature;

import java.util.Base64;

import javax.crypto.Cipher;

public class SenderRSA {

    public String SenderRSAMessage() throws Exception {
        String Message = "test RSA using Signature (PublicKey,PrivateKey)";
        FileInputStream fileInputStream = new FileInputStream("otmane.jks");
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(fileInputStream, "otmane".toCharArray());
        Key key = keyStore.getKey("otmane", "otmane".toCharArray());
        PrivateKey privateKey = (PrivateKey) key;
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(Message.getBytes());
        String encryptedMessage = Base64.getEncoder().encodeToString(bytes);
        System.out.println(encryptedMessage);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey, new SecureRandom());
        signature.update(encryptedMessage.getBytes());
        byte[] sign = signature.sign();
        String SignatureForMessage = Base64.getEncoder().encodeToString(sign);
        String MS = encryptedMessage + "_.._" + SignatureForMessage;
        return MS;
    }

    public static void main(String[] args) throws Exception {
        SenderRSA senderRSA = new SenderRSA();
        System.out.println(senderRSA.SenderRSAMessage());
    }
}
