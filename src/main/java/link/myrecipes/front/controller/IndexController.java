package link.myrecipes.front.controller;

import link.myrecipes.front.dto.Recipe;
import link.myrecipes.front.service.IndexService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {
    @Value("${app.image-path.recipe}")
    private String recipeImagePath;

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

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        List<Recipe> popularRecipeList = this.indexService.readPopularRecipeList();

        Sort.Direction direction;
        if (isDescending) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }
        PageRequest pageRequest = PageRequest.of(0, this.pageSize, direction, this.sortField);
        List<Recipe> newRecipeList = this.indexService.readRecipeList(pageRequest);

        int recipePageCount = this.indexService.readRecipePageCount();

        model.addAttribute("popularRecipeList", popularRecipeList);
        model.addAttribute("newRecipeList", newRecipeList);
        model.addAttribute("recipePageCount", recipePageCount);
        model.addAttribute("recipeImagePath", this.recipeImagePath);
        return "index";
    }

    @GetMapping("/index/ajax")
    public String indexAjax(Model model, @RequestParam int nextPage) {
        PageRequest pageRequest = PageRequest.of(nextPage, this.pageSize, Sort.Direction.DESC, this.sortField);
        List<Recipe> newRecipeList = this.indexService.readRecipeList(pageRequest);

        model.addAttribute("newRecipeList", newRecipeList);
        model.addAttribute("recipeImagePath", this.recipeImagePath);
        return "index-ajax";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
