package com.coupon.issuecouponservice.util;

import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PaginationUtils {
    private final int currentPage;
    private final int totalPages;
    private final int pageGroupSize;
    private final int startPage;
    private final int endPage;
    private final int currentGroup;
    private final boolean isFirstGroup;
    private final boolean isLastGroup;
    private final int totalGroups;

    public PaginationUtils(Page<?> page, int pageGroupSize) {
        this.currentPage = page.getNumber() + 1;
        this.totalPages = page.getTotalPages();
        this.pageGroupSize = pageGroupSize;

        this.currentGroup = (currentPage - 1) / pageGroupSize;
        this.startPage = currentGroup * pageGroupSize + 1;
        this.endPage = Math.min(startPage + pageGroupSize - 1, totalPages);
        this.totalGroups = (int) Math.ceil((double) totalPages / pageGroupSize);

        this.isFirstGroup = currentGroup == 0;
        this.isLastGroup = currentGroup == totalGroups - 1;
    }
}