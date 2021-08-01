package com.example.tourappapi.utils.pagination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.LinkedList;
import java.util.List;

public class PagingUtil {
    public static Pageable preparePage(Integer pageNo, Integer pageSize) {
        return PageRequest.of(pageNo-1, pageSize, Sort.by("id"));
    }

    public static <T> List<T> getResult(Page<T> pageResult) {
        if (pageResult.hasContent()) {
            return pageResult.getContent();
        } else {
            return new LinkedList<>();
        }
    }
}