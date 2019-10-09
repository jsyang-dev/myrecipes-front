package io.myrecipes.front.controller;

import io.myrecipes.front.dto.Material;
import io.myrecipes.front.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        List<Material> materialList = this.recipeService.readMaterialList();
        model.addAttribute("materialList", materialList);

        return "recipe/register";
    }
}
