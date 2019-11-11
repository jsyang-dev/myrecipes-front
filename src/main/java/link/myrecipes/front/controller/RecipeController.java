package link.myrecipes.front.controller;

import link.myrecipes.front.dto.Material;
import link.myrecipes.front.dto.Recipe;
import link.myrecipes.front.dto.request.RecipeRequest;
import link.myrecipes.front.dto.view.RecipeView;
import link.myrecipes.front.service.RecipeService;
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

    @Value("${app.image-path.recipe}")
    private String recipeImagePath;

    @Value("${app.image-path.step}")
    private String stepImagePath;

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/view/{id}")
    public String view(Model model, @PathVariable int id) {
        RecipeView recipeView = this.recipeService.readRecipe(id);
        model.addAttribute("recipeView", recipeView);
        model.addAttribute("recipeImagePath", this.recipeImagePath);
        model.addAttribute("stepImagePath", this.stepImagePath);

        return "recipe/view";
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

    @GetMapping("/modify/{id}")
    public String modify(Model model, @PathVariable int id) {
        RecipeView recipeView = this.recipeService.readRecipe(id);
        List<Material> materialList = this.recipeService.readMaterialList();

        model.addAttribute("recipeView", recipeView);
        model.addAttribute("materialList", materialList);
        model.addAttribute("commonImagePath", this.commonImagePath);
        model.addAttribute("recipeImagePath", this.recipeImagePath);
        model.addAttribute("stepImagePath", this.stepImagePath);

        return "recipe/modify";
    }

    @PostMapping("/modify/ajax/{id}")
    @ResponseBody
    public Recipe modifyAjax(@PathVariable int id, @RequestBody @Valid RecipeRequest recipeRequest) {
        return this.recipeService.updateRecipe(id, recipeRequest);
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        this.recipeService.deleteRecipe(id);
        return "redirect:/";
    }

    @PostMapping("/upload/ajax")
    @ResponseBody
    public String uploadAjax(@RequestParam MultipartFile file, @RequestParam String path) {
        return this.recipeService.uploadImage(file, path);
    }
}
