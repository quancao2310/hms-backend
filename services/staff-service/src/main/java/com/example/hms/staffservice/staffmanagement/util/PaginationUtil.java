package com.example.hms.staffservice.staffmanagement.util;

import com.example.hms.staffservice.staffmanagement.dto.PaginatedResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public class PaginationUtil {
    
    public static <T> PaginatedResponse<T> toPaginatedResponse(Page<T> page) {
        return PaginatedResponse.<T>builder()
                .content(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .empty(page.isEmpty())
                .build();
    }
} 