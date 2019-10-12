package io.myrecipes.front.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class RecipeMaterial {
    private Integer materialId;

//    @NotBlank(message = "요리재료의 수량을 선택해주세요.")
    private Integer quantity;

    @Builder
    public RecipeMaterial(Integer materialId, Integer quantity) {
        this.materialId = materialId;
        this.quantity = quantity;
    }
}
