package com.devh.common.api.search.vo;

import java.util.ArrayList;
import java.util.List;

import com.devh.common.api.search.constant.Paging;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <pre>
 * Description :
 *     검색 요청 관련 클래스
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-02-10
 * </pre>
 */
@Getter
@Setter
@ToString
public class SearchParameterVO implements Pageable {
    
    /* Paging */
    private int page;
    private int size;
    private int rows;
    private int offset;
    /* return용 */
    private long total;

    /* Search */
    private long searchFrom;
    private long searchTo;
    private String sortIndex;
    private String sortOrder;
    private List<SearchConditionVO> searchConditionVOList;

    /* Default Constructor */
    public SearchParameterVO() {
        this.page = 1;
        this.size = Paging.DEFAULT_SIZE.getIntValue();
        this.rows = Paging.DEFAULT_ROWS.getIntValue();
        this.sortOrder = "desc";
        this.searchConditionVOList = new ArrayList<>();
        calculateOffset();
    }

    public void calculateOffset() {
        this.offset = (this.page - 1) * this.rows;
    }

    public Pageable getPageable(Sort sort) {
        return PageRequest.of(this.page - 1, this.rows, sort);
    }

    public boolean isAll() {
        return this.searchFrom <= 0 &&
                this.searchTo <= 0 &&
                searchConditionVOList.size() == 0;
    }

    public boolean isFromOrToOnly() {
        return isToOnly() || isFromOnly();
    }

    private boolean isToOnly() {
        return this.searchFrom <= 0 && this.searchTo > 0;
    }

    private boolean isFromOnly() {
        return this.searchFrom > 0 && this.searchTo <= 0;
    }

    public String getSortOrder() {
        return this.sortOrder;
    }

    /* Setter Override */
    public void setPage(int page) {
        this.page = page;
        calculateOffset();
    }

    public void setRows(int rows) {
        this.rows = rows;
        calculateOffset();
    }

    /* Pageable Implements */
    @Override
    public int getPageNumber() {
        return this.page - 1;
    }

    @Override
    public int getPageSize() {
        return this.rows;
    }

    @Override
    public long getOffset() {
        return this.offset;
    }

    @Override
    public Sort getSort() {
        Sort.Direction direction = "desc".equalsIgnoreCase(this.sortOrder) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return Sort.by(direction, this.sortIndex);
    }

    @Override
    public Pageable next() {
        return PageRequest.of(this.getPageNumber() + 1, this.getPageSize(), this.getSort());
    }

    @Override
    public Pageable previousOrFirst() {
        return this.hasPrevious() ? withPage(this.getPageNumber() - 1) : first();
    }

    @Override
    public Pageable first() {
        return PageRequest.of(0, this.getPageSize(), this.getSort());
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return PageRequest.of(pageNumber, this.getPageSize(), this.getSort());
    }

    @Override
    public boolean hasPrevious() {
        return this.getPageNumber() > 0;
    }
}
