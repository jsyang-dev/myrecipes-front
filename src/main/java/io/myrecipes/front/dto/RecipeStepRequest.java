package io.myrecipes.front.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class RecipeStepRequest {
    private Integer step;

    @NotBlank(message = "요리 순서의 내용을 입력해주세요.")
    private String content;

    @Length(max = 200, message = "요리 순서는 최대 200자까지 입력이 가능합니다.")
    private String image;

    @Builder
    public RecipeStepRequest(Integer step, String content, String image) {
        this.step = step;
        this.content = content;
        this.image = image;
    }
}
