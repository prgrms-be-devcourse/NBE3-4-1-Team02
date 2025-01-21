package com.example.nbe341team02.global.page;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

@UtilityClass
public class PageableUtils {
    public static Pageable createPageable(PageRequestDto pageRequest, int defaultPageSize) {
        int page = Objects.requireNonNullElse(pageRequest.page(), 1);
        int size = Objects.requireNonNullElse(pageRequest.size(), defaultPageSize);

        return PageRequest.of(page - 1, size);
    }
}
