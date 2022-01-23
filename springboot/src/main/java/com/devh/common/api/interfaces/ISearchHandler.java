package com.devh.common.api.interfaces;

import com.devh.common.api.search.vo.SearchParameterVO;

import org.springframework.data.domain.Pageable;

public interface ISearchHandler {
    Pageable getPageable(SearchParameterVO searchParameterVO);
}
