package com.devh.common.api.search.constant;

import lombok.Getter;

@Getter
public enum SearchDataType {
    STRING("string"),
    INTEGER("integer"),
    LONG("long"),
    DOUBLE("double"),
    STRING_ARRAY("string_array"),
    INTEGER_ARRAY("integer_array"),
    LONG_ARRAY("long_array"),
    DOUBLE_ARRAY("double");


    private String value;

    private SearchDataType(String value) {
        this.value = value;
    }
}
