package link.myrecipes.front.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class PageParam implements Serializable {
    private int page;
    private int size;
    private String sortField;
    private boolean isDescending;

    @Builder
    public PageParam(int page, int size, String sortField, boolean isDescending) {
        this.page = page;
        this.size = size;
        this.sortField = sortField;
        this.isDescending = isDescending;
    }
}
