package com.devh.common.util;

import org.json.simple.JSONObject;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * <pre>
 * Description :
 *     Exception 관련 유틸
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-04-10
 * </pre>
 */
public class ExceptionUtils {

    /**
     * <pre>
     * Description :
     *     예외 처리 공통화를 위한 Enum
     *
     * ===============================================
     * Member fields :
     *     MESSAGE("message")
     *     STACK_TRACE("stackTrace")
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-03-21
     * </pre>
     */
    public enum ExceptionKey {
        MESSAGE("message"),
        STACK_TRACE("stackTrace");

        private final String key;
        ExceptionKey(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    /*
     * <pre>
     * Description :
     *     익셉션의 스택트레이스를 문자열로 반환
     * ===============================
     * Parameters :
     *     Throwable cause
     * Returns :
     *     String
     * Throws :
     *
     * ===============================
     *
     * Author : HeonSeung Kim
     * Date   : 2021. 5. 17.
     * </pre>
     */
    public static String stackTraceToString(Throwable cause) {
        final StringWriter sw = new StringWriter();
        cause.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    /**
     * <pre>
     * Description
     *     Logger와 Exception을 전달받아 특정한 포맷의 error로그 출력
     * ===============================================
     * Parameters :
     *     Logger logger
     *     Exception e
     * Returns :
     *     void
     * Throws :
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-04-10
     * </pre>
     */
    public static void printErrorLogWithException(Logger logger, Exception e) {
        logger.error("=====================ERROR======================");
        logger.error("\n"+stackTraceToString(e));
        logger.error("=====================ERROR======================");
    }

    /**
     * <pre>
     * Description
     *     Exception stackTrace를 JSONObject로 반환
     * ===============================================
     * Parameters :
     *     Logger logger
     *     Exception e
     * Returns :
     *     void
     * Throws :
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-03-10
     * </pre>
     */
    public static JSONObject toJson(Exception e) {

        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));

        BufferedReader br = new BufferedReader(
                new StringReader(
                        sw.toString().replaceAll("\t", "    ")
                )
        );

        ArrayList<String> stackTraceList = new ArrayList<>();

        try {

            String line;

            while( (line = br.readLine()) != null )
                stackTraceList.add(line);

        } catch (Exception e1) {
            e1.printStackTrace();
        }

        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();

        linkedHashMap.put(ExceptionKey.MESSAGE.getKey(), e.getMessage());
        linkedHashMap.put(ExceptionKey.STACK_TRACE.getKey(), stackTraceList);

        return new JSONObject(linkedHashMap);
    }
}
