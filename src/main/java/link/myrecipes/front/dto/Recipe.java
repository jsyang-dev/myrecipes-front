package link.myrecipes.front.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
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
