package com.devh.common.api.search.constant;

import lombok.Getter;

@Getter
public enum SearchOperator {
    GRATER_THAN("gt"),
    GRATER_THAN_EQUAL("gte"),
    LESS_THAN("lt"),
    LESS_THAN_EQUAL("lte"),
    EQUAL("eq"),
    NOT_EQUAL("ne"),
    LIKE("like"),
    IN("in"),
    NOT_IN("notIn");

    private String dslName;

    private SearchOperator(String dslName) {
        this.dslName = dslName;
    }
}
