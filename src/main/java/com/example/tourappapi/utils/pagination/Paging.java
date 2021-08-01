package com.example.tourappapi.utils.pagination;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Paging<T> {
    private Long pageCount;
    private Long total;
    private List<T> items;
}