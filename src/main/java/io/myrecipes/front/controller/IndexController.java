package io.myrecipes.front.controller;

import io.myrecipes.front.domain.PageParam;
import io.myrecipes.front.domain.Recipe;
import io.myrecipes.front.service.IndexService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {
    @Value("${app.img-path}")
    private String imgPath;

    @Value("${app.index.page-size}")
    private int pageSize;

    @Value("${app.index.sort-field}")
    private String sortField;

    @Value("${app.index.is-descending}")
    private boolean isDescending;

    private final IndexService indexService;

    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        PageParam pageParamForPopularList = new PageParam(0, this.pageSize, this.sortField, this.isDescending);
        List<Recipe> popularRecipeList = this.indexService.readRecipeList(pageParamForPopularList);

        PageParam pageParamForNewList = new PageParam(0, this.pageSize, this.sortField, this.isDescending);
        List<Recipe> newRecipeList = this.indexService.readRecipeList(pageParamForNewList);

        model.addAttribute("popularRecipeList", popularRecipeList);
        model.addAttribute("newRecipeList", newRecipeList);
        model.addAttribute("imgPath", this.imgPath);
        return "index";
    }
}
