package io.myrecipes.front.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class RecipeMaterial {
    private Integer materialId;

    @NotNull(message = "요리 재료의 수량을 선택해주세요.")
    @Positive(message = "요리 재료의 수량을 양수로 입력해주세요.")
    private Integer quantity;

    @Builder
    public RecipeMaterial(Integer materialId, Integer quantity) {
        this.materialId = materialId;
        this.quantity = quantity;
    }
}
