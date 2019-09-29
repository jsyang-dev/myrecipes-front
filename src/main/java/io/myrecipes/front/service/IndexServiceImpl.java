package io.myrecipes.front.service;

import io.myrecipes.front.common.RestTemplateHelperImpl;
import io.myrecipes.front.dto.PageParam;
import io.myrecipes.front.dto.Recipe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {
    @Value("${app.api.recipe}")
    private String api;

    @Value("${app.index.page-size}")
    private int pageSize;

    private final RestTemplateHelperImpl restService;

    public IndexServiceImpl(RestTemplateHelperImpl restService) {
        this.restService = restService;
    }

    @Override
    public List<Recipe> readRecipeList(PageParam pageParam) {
        String url = api + "/recipes"
                        + "?page=" + pageParam.getPage()
                        + "&size=" + pageParam.getSize()
                        + "&sortField=" + pageParam.getSortField()
                        + "&isDescending=" + pageParam.isDescending();

        return this.restService.getForList(Recipe.class, url);
    }

    @Override
    public long readRecipePageCnt() {
        String url = api + "/recipes/cnt";
        long recipeCnt = this.restService.getForObject(Long.class, url);

        return Math.round((double) recipeCnt / pageSize);
    }
}
