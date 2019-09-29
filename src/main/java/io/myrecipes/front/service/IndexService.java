package io.myrecipes.front.service;

import io.myrecipes.front.dto.PageParam;
import io.myrecipes.front.dto.Recipe;

import java.util.List;

public interface IndexService {
    List<Recipe> readRecipeList(PageParam pageParam);

    long readRecipePageCnt();
}
