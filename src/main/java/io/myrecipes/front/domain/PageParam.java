package io.myrecipes.front.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageParam {
    private int page;
    private int size;
    private String sortField;
    private boolean isDescending;
}
