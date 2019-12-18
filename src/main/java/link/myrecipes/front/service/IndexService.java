package link.myrecipes.front.service;

import link.myrecipes.front.dto.PageParam;
import link.myrecipes.front.dto.Recipe;

import java.util.List;

public interface IndexService {
    List<Recipe> readRecipeList(PageParam pageParam);

    int readRecipePageCount();
}
