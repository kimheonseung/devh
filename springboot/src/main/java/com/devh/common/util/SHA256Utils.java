package com.devh.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/*
 * <pre>
 * Description :
 *     SHA256 암호화 관련 클래스
 * ===============================
 * Memberfields :
 *
 * ===============================
 *
 * Author : HeonSeung Kim
 * Date   : 2021. 4. 1.
 * </pre>
 */
@Slf4j
public class SHA256Utils {
    private static final String HASH = "SHA-256";

    /*
     * <pre>
     * Description :
     *     SHA256 해시 문자열을 반환
     * ===============================
     * Parameters :
     *     Object obj     String / File / RandomAccessFile
     * Returns :
     *     SHA256 암호화된 문자열 해시값
     * Throws :
     *     Exception     String, File, RandomAccessFile 객체가 아닌 경우
     * ===============================
     *
     * Author : HeonSeung Kim
     * Date   : 2021. 4. 1.
     * </pre>
     */
    public static String getHashString(Object obj) throws Exception {

        String result = null;

        if (obj instanceof String)
            result = createHash((String) obj);
        else if(obj instanceof File)
            result = createHash((File) obj);
        else if(obj instanceof RandomAccessFile)
            result = createHash((RandomAccessFile) obj);
        else
            log.error("Hash Failed.");

        return result;

    }


    private static String createHash(String target){

        String result = null;
        try {

            MessageDigest sh = MessageDigest.getInstance(HASH);
            sh.update(target.getBytes());
            result = convertHashByteArrayToString(sh.digest());

        } catch (NoSuchAlgorithmException e) {
            log.error(ExceptionUtils.stackTraceToString(e));
        }

        return result;
    }

    /*
     * <pre>
     * Description :
     *     File에 대한 SHA256 해싱
     * ===============================
     * Parameters :
     *     File file
     * Returns :
     *     File의 해시값
     * Throws :
     *     FileNotFoundException
     * ===============================
     *
     * Author : HeonSeung Kim
     * Date   : 2021. 4. 1.
     * </pre>
     */
    private static String createHash(File file) throws FileNotFoundException {

        String result = null;
        int buff = 16384;

        try {

            MessageDigest digest = MessageDigest.getInstance(HASH);
            InputStream fis = new FileInputStream(file);
            int n = 0;
            byte[] buffer = new byte[buff];

            while (n != -1) {

                n = fis.read(buffer);
                if (n > 0) {
                    digest.update(buffer, 0, n);
                }
            }

            result = convertHashByteArrayToString(digest.digest());
            fis.close();
        }catch (FileNotFoundException e) {
            log.error(ExceptionUtils.stackTraceToString(e));
            throw new FileNotFoundException();
        } catch (Exception e) {
            log.error(ExceptionUtils.stackTraceToString(e));
        }

        return result;
    }

    /*
     * <pre>
     * Description :
     *     RandomAccessFile에 대한 SHA256 해싱
     * ===============================
     * Parameters :
     *     RandomAccessFile file
     * Returns :
     *     File의 해시값
     * Throws :
     *
     * ===============================
     *
     * Author : HeonSeung Kim
     * Date   : 2021. 4. 1.
     * </pre>
     */
    private static String createHash(RandomAccessFile file) {

        String result = null;
        int buff = 16384;

        try {

            MessageDigest hashSum = MessageDigest.getInstance(HASH);

            byte[] buffer = new byte[buff];
            byte[] byteData = null;

            long read = 0;

            /* calculate the hash of the hole file for the test */
            long offset = file.length();
            int unitsize;

            while (read < offset) {
                unitsize = (int) (((offset - read) >= buff) ? buff : (offset - read));
                file.read(buffer, 0, unitsize);

                hashSum.update(buffer, 0, unitsize);

                read += unitsize;
            }

            file.close();
            byteData = new byte[hashSum.getDigestLength()];
            byteData = hashSum.digest();

            result = convertHashByteArrayToString(byteData);

        } catch (IOException e) {
            log.error(ExceptionUtils.stackTraceToString(e));
        } catch (NoSuchAlgorithmException e) {
            log.error(ExceptionUtils.stackTraceToString(e));
        }

        return result;
    }


    /*
     * <pre>
     * Description :
     *     salt 생성 메소드
     * ===============================
     * Parameters :
     *
     * Returns :
     *     salt값
     * Throws :
     *
     * ===============================
     *
     * Author : HeonSeung Kim
     * Date   : 2021. 4. 1.
     * </pre>
     */
    public static String generateSalt() {

        final Random random = new Random();

        byte[] salt = new byte[8];
        random.nextBytes(salt);

        final StringBuffer sb = new StringBuffer();

        for (byte b : salt)
            sb.append(String.format("%02x",b));

        return sb.toString();
    }


    /*
     * <pre>
     * Description :
     *     해싱된 바이트배열을 문자열로 반환
     * ===============================
     * Parameters :
     *     byte[] hashByteArray
     * Returns :
     *     해시 문자열
     * Throws :
     *
     * ===============================
     *
     * Author : HeonSeung Kim
     * Date   : 2021. 4. 1.
     * </pre>
     */
    private static String convertHashByteArrayToString(byte[] hashByteArray){

        StringBuffer sb = new StringBuffer();
        for(int i = 0 ; i < hashByteArray.length ; i++)
            sb.append(Integer.toString((hashByteArray[i]&0xff) + 0x100, 16).substring(1));

        return sb.toString();

    }
}
