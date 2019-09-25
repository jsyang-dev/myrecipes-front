package io.myrecipes.front.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Recipe {
    private Integer id;

    private String title;

    private String image;

    private Integer estimatedTime;

    private Integer difficulty;

    private List<RecipeTag> recipeTagList = new ArrayList<>();

    @Builder
    public Recipe(Integer id, String title, String image, Integer estimatedTime, Integer difficulty) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.estimatedTime = estimatedTime;
        this.difficulty = difficulty;
    }
}
