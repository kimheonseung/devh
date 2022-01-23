package com.devh.common.util;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;

/**
 * <pre>
 * Description :
 *     AES256 암호화 관련 유틸
 * ===============================
 * Memberfields :
 *
 * ===============================
 *
 * Author : HeonSeung Kim
 * Date   : 2021. 11. 9.
 * </pre>
 */
public class AES256Utils {
    private static final String KEY = "devh";

    /**
     * <pre>
     * Description :
     *     솔트값을 받아 비밀키 생성
     * ===============================
     * Parameters :
     *
     * Returns :
     *     SecretKeySpec
     * Throws :
     *     InvalidKeySpecException
     *     NoSuchAlgorithmException
     *     InvalidKeyException
     * ===============================
     *
     * Author : HeonSeung Kim
     * Date   : 2021. 11. 9.
     * </pre>
     */
    private static SecretKeySpec getSecretKeySpec(byte[] saltBytes) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException {
        /* Password-Based Key Derivation function */
        final SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        /* 1000번 해시하여 256 bit 키 spec 생성*/
        final PBEKeySpec spec = new PBEKeySpec(KEY.toCharArray(), saltBytes, 1000, 256);
        /* 비밀키 생성 */
        final SecretKey secretKey = factory.generateSecret(spec);

        return new SecretKeySpec(secretKey.getEncoded(), "AES");
    }

    /**
     * <pre>
     * Description :
     *     솔트 생성
     * ===============================
     * Parameters :
     *
     * Returns :
     *     byte[20]
     * Throws :
     *
     * ===============================
     *
     * Author : HeonSeung Kim
     * Date   : 2021. 11. 9.
     * </pre>
     */
    private static byte[] createSaltBytes(){
        final SecureRandom random = new SecureRandom();
        byte saltBytes[] = new byte[20];
        random.nextBytes(saltBytes);

        return saltBytes;
    }

    /**
     * <pre>
     * Description :
     *     암/복호화에 필요한 Cipher 반환
     * ===============================
     * Parameters :
     *
     * Returns :
     *     Cipher
     * Throws :
     *     NoSuchAlgorithmException
     *     NoSuchPaddingException
     * ===============================
     *
     * Author : HeonSeung Kim
     * Date   : 2021. 11. 9.
     * </pre>
     */
    private static Cipher getCipher() throws NoSuchAlgorithmException, NoSuchPaddingException {
        return Cipher.getInstance("AES/CBC/PKCS5Padding");	/* 알고리즘/모드/패딩 */
    }

    /**
     * <pre>
     * Description :
     *     문자열 암호화
     *     문자열이 비어있는 경우 null 반환
     * ===============================
     * Parameters :
     *     String msg
     * Returns :
     *     String
     * Throws :
     *     NoSuchAlgorithmException
     *     InvalidKeySpecException
     *     NoSuchPaddingException
     *     InvalidKeyException
     *     InvalidParameterSpecException
     *     UnsupportedEncodingException
     *     BadPaddingException
     *     IllegalBlockSizeException
     * ===============================
     *
     * Author : HeonSeung Kim
     * Date   : 2021. 11. 9.
     * </pre>
     */
    public static String encrypt(String msg) throws NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, InvalidKeyException,
            InvalidParameterSpecException, UnsupportedEncodingException,
            BadPaddingException, IllegalBlockSizeException {

        if(StringUtils.isNotEmpty(msg)){

            final byte[] saltBytes = createSaltBytes();

            final SecretKeySpec secret = getSecretKeySpec(saltBytes);

            final Cipher cipher = getCipher();
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            final AlgorithmParameters params = cipher.getParameters();

            byte[] encryptedTextBytes = cipher.doFinal(msg.getBytes("UTF-8"));

            /* Initial Vector(1단계 암호화 블록용) */
            byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();

            /* salt + Initial Vector + 암호문 결합 후 Base64인코딩 */
            byte[] buffer = new byte[saltBytes.length + ivBytes.length + encryptedTextBytes.length];
            System.arraycopy(saltBytes, 0, buffer, 0, saltBytes.length);
            System.arraycopy(ivBytes, 0, buffer, saltBytes.length, ivBytes.length);
            System.arraycopy(encryptedTextBytes, 0, buffer, saltBytes.length + ivBytes.length, encryptedTextBytes.length);

			return Base64.getEncoder().encodeToString(buffer);

        } else
            return null;

    }


    /**
     * <pre>
     * Description :
     *     문자열 복호화
     *     문자열이 비어있는 경우 null 반환
     * ===============================
     * Parameters :
     *     String msg
     * Returns :
     *     String
     * Throws :
     *     NoSuchPaddingException
     *     NoSuchAlgorithmException
     *     InvalidKeySpecException
     *     InvalidAlgorithmParameterException
     *     InvalidKeyException
     *     BadPaddingException
     *     IllegalBlockSizeException
     * ===============================
     *
     * Author : HeonSeung Kim
     * Date   : 2021. 11. 9.
     * </pre>
     */
    public static String decrypt(String msg) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeySpecException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        if(StringUtils.isNotEmpty(msg)){

            final Cipher cipher = getCipher();
			final ByteBuffer buffer = ByteBuffer.wrap(Base64.getDecoder().decode(msg));

            final byte[] saltBytes = createSaltBytes();
            buffer.get(saltBytes, 0, saltBytes.length);

            final byte[] ivBytes = new byte[cipher.getBlockSize()];
            buffer.get(ivBytes, 0, ivBytes.length);

            final byte[] encryoptedTextBytes = new byte[buffer.capacity() - saltBytes.length - ivBytes.length];
            buffer.get(encryoptedTextBytes);

            final SecretKeySpec secret = getSecretKeySpec(saltBytes);

            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));

            byte[] decryptedTextBytes = cipher.doFinal(encryoptedTextBytes);
            return new String(decryptedTextBytes);

        } else
            return null;

    }
}
