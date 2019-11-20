package link.myrecipes.front.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class RecipeTag implements Serializable {
    private String tag;

    @Builder
    public RecipeTag(String tag) {
        this.tag = tag;
    }
}
