package link.myrecipes.front.service;

import link.myrecipes.front.dto.Recipe;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IndexService {
    List<Recipe> readRecipeList(PageRequest pageRequest);

    int readRecipePageCount();

    List<Recipe> readPopularRecipeList();
}
