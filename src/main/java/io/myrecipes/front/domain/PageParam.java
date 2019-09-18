package io.myrecipes.front.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageParam {
    private int page;
    private int size;
    private String sortField;
    private boolean isDescending;
}
