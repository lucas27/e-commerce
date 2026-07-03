package com.projeto.e_commerce.auth.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
// import java.security.PrivateKey;
// import java.security.PublicKey;

import org.springframework.stereotype.Component;

@Component
public class CreateKey {

    public KeyPair createKeyasymmetric() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        
        // KeyPair pair = keyGen.generateKeyPair();

        // PublicKey publicKey = pair.getPublic();
        // PrivateKey privateKey = pair.getPrivate();
        return keyGen.generateKeyPair();
    }

}
