package com.example.digitalsignatureAESRSA.AES;

import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SenderAES {

    public String MessageSender() throws Exception {
        String Message = "That's my projet for testing  AES";
        String Secret = "azerty";
        String MessageEncrypted = Base64.getEncoder().encodeToString(Message.getBytes());
        SecretKeySpec SecretKeySpec = new SecretKeySpec(Secret.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(SecretKeySpec);
        byte[] signature = mac.doFinal(MessageEncrypted.getBytes());
        String signatureTest = Base64.getEncoder().encodeToString(signature);
        String SignMessage = MessageEncrypted + "_.._" + signatureTest;
        return SignMessage;
    }

    public static void main(String[] args) throws Exception {
        SenderAES senderAES = new SenderAES();
        System.out.println(senderAES.MessageSender());
    }
}
