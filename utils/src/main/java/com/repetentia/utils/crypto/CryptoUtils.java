package com.repetentia.utils.crypto;

import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;

public class CryptoUtils {

    public static String Alg = "AES/CBC/PKCS5Padding";
    public static String PK = "01234567890123456789012345678901"; // 32byte
//    public static String IV = PK.substring(0,16); // 16byte
    public static BytesKeyGenerator generator = KeyGenerators.shared(16);
    public static byte[] iv = generator.generateKey();

    public static String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(Alg);
//        BytesKeyGenerator generator = KeyGenerators.shared(16);
//        byte[] iv = generator.generateKey();
        System.out.println(Arrays.toString(iv));
        SecretKeySpec keySpec = new SecretKeySpec(iv, "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance(Alg);
//        BytesKeyGenerator generator = KeyGenerators.shared(16);
//        byte[] iv = generator.generateKey();
        System.out.println(Arrays.toString(iv));
        SecretKeySpec keySpec = new SecretKeySpec(iv, "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decodedBytes);
        return new String(decrypted, "UTF-8");
    }

    public static void main(String[] args) throws Exception {
        String text = "10:22:33,852 [] [] [] [] [restartedMain] [] [INFO] org.quartz.impl.StdSchedulerFactoryQuartz scheduler 'quartzScheduler' initialized from an externally provided properties instance.";
        String encrypt = encrypt(text);
        System.out.println(encrypt);
        String decrypt = decrypt(encrypt);
        System.out.println(decrypt);
        String ex = "5VcXujPEqlCBL67FFNbkSEGb2DAVUK/WOZvUryXBZMUPulT9ZfzzES+D4BPb3H+r0cJZ7Lr4uOxdcK6+PRthsuctmXR/vuLF+isRQigsoLUoH0+vuQy/y64uPltuFAf6nQW1ecUH6qgnklS+h0AGq01IE1dOoI1LdYtRi7mb1xGnpops6bczyC0SjCOH1aGgVJdrNLrXN1QtoGKrTQcXGS79/cr3HtYpHw79KB7rOe/OPmPTClM3USRJATkHYm2X";
        String decryptEx = decrypt(ex);
        System.out.println(decryptEx);
    }
}
