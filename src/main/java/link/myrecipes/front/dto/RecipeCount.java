package link.myrecipes.front.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecipeCount {

    private long count;

    public RecipeCount(long count) {
        this.count = count;
    }
}
