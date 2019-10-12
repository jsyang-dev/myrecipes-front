package io.myrecipes.front.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class RecipeStep {
    private Integer step;

//    @NotBlank(message = "요리 순서의 내용을 입력해주세요.")
    private String content;

    private String image;

    @Builder
    public RecipeStep(Integer step, String content, String image) {
        this.step = step;
        this.content = content;
        this.image = image;
    }
}
