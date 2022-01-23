package com.devh.common.api.response;

import com.devh.common.util.ExceptionUtils;

import org.json.simple.JSONObject;

/**
 * <pre>
 * Description :
 *     REST 호출을 통해 반환되는 결과를 담은 Map
 *     정상 결과 리턴
 *         ResultToJsonConverter cvt = ResultToJsonConverter.init();
 *         cvt.putResultMapToResultJson(someObject);
 *         return cvt.get();
 *         => result.resultJson
 *
 *     예외 결과 리턴
 *         ResultToJsonConverter cvt = ResultToJsonConverter.init();
 *         cvt.putResultMapToExceptionInformation(e);
 *         return cvt.get();
 *         => result.resultException
 * ===============================================
 * Member fields :
 *     JSONObject resultJson
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-03-21
 * </pre>
 */
@SuppressWarnings("unchecked")
public class ResultToJsonConverter {

    private final JSONObject resultJson;

    private ResultToJsonConverter() {
        this.resultJson = new JSONObject();
    }

    public static ResultToJsonConverter init() {
        return new ResultToJsonConverter();
    }

    public void putResultMapToResultJson(Object resultObject) {
        this.resultJson.put("resultJson", resultObject);
    }

    public void putResultMapToExceptionInformation(Exception e) {
        this.resultJson.put("resultException", ExceptionUtils.toJson(e));
    }

    public JSONObject get() {
        return this.resultJson;
    }
}
