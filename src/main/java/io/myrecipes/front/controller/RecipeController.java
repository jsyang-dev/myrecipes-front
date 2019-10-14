package io.myrecipes.front.controller;

import io.myrecipes.front.dto.Material;
import io.myrecipes.front.dto.Recipe;
import io.myrecipes.front.dto.RecipeRequest;
import io.myrecipes.front.service.RecipeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/recipe")
public class RecipeController {
    @Value("${app.image-path.common}")
    private String commonImagePath;

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        List<Material> materialList = this.recipeService.readMaterialList();
        model.addAttribute("materialList", materialList);
        model.addAttribute("commonImagePath", this.commonImagePath);

        return "recipe/register";
    }

    @PostMapping("/register/ajax")
    @ResponseBody
    public Recipe registerAjax(@RequestBody @Valid RecipeRequest recipeRequest) {
        return this.recipeService.createRecipe(recipeRequest);
    }

    @PostMapping("/upload/ajax")
    @ResponseBody
    public String uploadAjax(@RequestParam MultipartFile file, @RequestParam String path) {
        return this.recipeService.uploadImage(file, path);
    }
}
