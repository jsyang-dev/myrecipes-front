package io.myrecipes.front.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class Recipe {
    private Integer id;
    private String title;
    private String image;
    private Integer estimatedTime;
    private String difficulty;
    private Integer registerUserId;
    private Timestamp registerDate;
    private Integer modifyUserId;
    private Timestamp modifyDate;
    private List<RecipeTag> recipeTagList = new ArrayList<>();

    public Recipe(String title, String image, Integer estimatedTime, String difficulty, Integer registerUserId) {
        this.title = title;
        this.image = image;
        this.estimatedTime = estimatedTime;
        this.difficulty = difficulty;
        this.registerUserId = registerUserId;
    }
}
