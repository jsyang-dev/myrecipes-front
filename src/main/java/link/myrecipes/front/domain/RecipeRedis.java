package link.myrecipes.front.domain;

import link.myrecipes.front.dto.RecipeTag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@RedisHash(timeToLive = 3600)
public class RecipeRedis {
    private Integer id;

    private String title;

    private String image;

    private Integer estimatedTime;

    private Integer difficulty;

    private List<RecipeTag> recipeTagList = new ArrayList<>();

    @Builder
    public RecipeRedis(Integer id, String title, String image, Integer estimatedTime, Integer difficulty) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.estimatedTime = estimatedTime;
        this.difficulty = difficulty;
    }
}
