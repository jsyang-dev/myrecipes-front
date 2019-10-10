package io.myrecipes.front.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class RecipeRequest {
    @NotBlank(message = "레시피 제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "레시피 이미지를 등록해주세요.")
    private String image;

    @NotBlank(message = "예상시간을 선택해주세요.")
    private Integer estimatedTime;

    @NotBlank(message = "난이도를 선택해주세요.")
    private Integer difficulty;

    private List<RecipeMaterial> recipeMaterialList = new ArrayList<>();

    private List<RecipeStep> recipeStepList = new ArrayList<>();

    private List<RecipeTag> recipeTagList = new ArrayList<>();

    @Builder
    public RecipeRequest(String title, String image, Integer estimatedTime, Integer difficulty) {
        this.title = title;
        this.image = image;
        this.estimatedTime = estimatedTime;
        this.difficulty = difficulty;
    }
}
