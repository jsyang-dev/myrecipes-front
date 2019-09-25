package io.myrecipes.front.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RecipeTag {
    private String tag;

    @Builder
    public RecipeTag(String tag) {
        this.tag = tag;
    }
}
