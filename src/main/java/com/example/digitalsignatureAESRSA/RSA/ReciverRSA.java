package com.example.digitalsignatureAESRSA.RSA;

import java.io.FileInputStream;

import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Base64;

import javax.crypto.Cipher;

public class ReciverRSA {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("publickey.cert");
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Certificate certificate = certificateFactory.generateCertificate(fileInputStream);
        PublicKey publicKey = certificate.getPublicKey();
        SenderRSA senderRSA = new SenderRSA();
        String MessageSignatureEncrypted = senderRSA.SenderRSAMessage();
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        String[] data = MessageSignatureEncrypted.split("_.._");
        String message = data[0];
        String SignVerify = data[1];
        byte[] decodeSignature = Base64.getDecoder().decode(SignVerify);
        signature.update(message.getBytes());
        boolean verify = signature.verify(decodeSignature);
        System.out.println("*******************************************");
        System.out.println(verify ? "signature Ok" : "Signature Not OK");
        if (verify == true) {
            System.out.println("*****************************************");
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] decodeEncryptedMessage = Base64.getDecoder().decode(message.getBytes());
            byte[] decodeEncrypted = cipher.doFinal(decodeEncryptedMessage);
            System.out.println(new String(decodeEncrypted));
        }

    }
}
