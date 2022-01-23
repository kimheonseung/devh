package com.devh.common.api.search.vo;

import com.devh.common.api.search.constant.SearchDataType;
import com.devh.common.api.search.constant.SearchOperator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <pre>
 * Description :
 *     검색 조건을 갖는 VO
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-08-25
 * </pre>
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchConditionVO {
    private String searchKey;
    private Object searchValue;
    private SearchDataType searchDataType;
    private SearchOperator searchOperator;
}
