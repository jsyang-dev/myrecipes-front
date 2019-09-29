package io.myrecipes.front.controller;

import io.myrecipes.front.dto.PageParam;
import io.myrecipes.front.dto.Recipe;
import io.myrecipes.front.service.IndexService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping({"/", "index"})
    public String index(Model model) {
        PageParam pageParamForPopularList = new PageParam(1, this.pageSize, this.sortField, this.isDescending);
        List<Recipe> popularRecipeList = this.indexService.readRecipeList(pageParamForPopularList);

        PageParam pageParamForNewList = new PageParam(1, this.pageSize, this.sortField, this.isDescending);
        List<Recipe> newRecipeList = this.indexService.readRecipeList(pageParamForNewList);

        long recipePageCnt = this.indexService.readRecipePageCnt();

        model.addAttribute("popularRecipeList", popularRecipeList);
        model.addAttribute("newRecipeList", newRecipeList);
        model.addAttribute("recipePageCnt", recipePageCnt);
        model.addAttribute("imgPath", this.imgPath);
        return "index";
    }

    @GetMapping("/index/ajax")
    // TODO: 파라미터를 DTO로 변경, Validation 체크 로직 추가
    public String indexAjax(Model model, @RequestParam int nextPage) {
        PageParam pageParam = new PageParam(nextPage, this.pageSize, this.sortField, this.isDescending);
        List<Recipe> newRecipeList = this.indexService.readRecipeList(pageParam);

        model.addAttribute("newRecipeList", newRecipeList);
        model.addAttribute("imgPath", this.imgPath);
        return "index-ajax";
    }

    @GetMapping("/view")
    public String view(Model model) {
        return "index";
    }
}
