package com.ls.in.global.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageNation {
    public static Pageable setPage(int currentPage, int pageSize) {
        Pageable pageable = null;
        try {
            pageable = PageRequest.of(currentPage, pageSize);
        } catch (Exception e) {
            pageable = PageRequest.of(0, 10);
        }
        return pageable;
    }
}
