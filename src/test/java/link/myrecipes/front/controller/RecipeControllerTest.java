package link.myrecipes.front.controller;

import link.myrecipes.front.dto.Material;
import link.myrecipes.front.dto.Recipe;
import link.myrecipes.front.dto.request.RecipeRequest;
import link.myrecipes.front.dto.view.RecipeView;
import link.myrecipes.front.service.RecipeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest extends ControllerTest {

    private Recipe recipe;
    private RecipeView recipeView;
    private List<Material> materialList;

    @Value("${app.image-path.recipe}")
    private String recipeImagePath;

    @Value("${app.image-path.step}")
    private String stepImagePath;

    @MockBean
    private RecipeServiceImpl recipeService;

    @Before
    public void setUp() {
        this.recipe = Recipe.builder()
                .id(1)
                .title("레시피")
                .image("recipe.jpg")
                .estimatedTime(30)
                .difficulty(1)
                .build();

        this.recipeView = this.modelMapper.map(this.recipe, RecipeView.class);

        this.materialList = new ArrayList<>();
        this.materialList.add(Material.builder()
                .id(10)
                .name("재료")
                .unitName("kg")
                .build());
    }

    @Test
    public void When_레시피_상세정보_페이지_조회_Then_정상_리턴() throws Exception {

        // Given
        given(this.recipeService.readRecipe(eq(this.recipe.getId()))).willReturn(this.recipeView);

        // When
        final ResultActions actions = this.mockMvc.perform(get("/recipe/view/" + this.recipe.getId()));

        // Then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/view"))
                .andExpect(model().attribute("recipeView", this.recipeView))
                .andExpect(model().attribute("recipeImagePath", this.recipeImagePath))
                .andExpect(model().attribute("stepImagePath", this.stepImagePath))
                .andExpect(content().string(containsString("_csrf")));
    }

    @Test
    @WithMockUser
    public void When_레시피_등록_페이지_조회_Then_정상_리턴() throws Exception {

        // Given
        given(this.recipeService.readMaterialList()).willReturn(this.materialList);

        // When
        final ResultActions actions = this.mockMvc.perform(get("/recipe/register"));

        // Then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/register"))
                .andExpect(model().attribute("materialList", this.materialList))
                .andExpect(content().string(containsString("_csrf")));
    }

    @Test
    @WithMockUser
    public void When_레시피_등록_Ajax_Then_정상_리턴() throws Exception {

        // Given
        String recipeJson = this.objectMapper.writeValueAsString(this.recipe);
        given(this.recipeService.createRecipe(any(RecipeRequest.class), any(Integer.class))).willReturn(this.recipe);

        // When
        final ResultActions actions = this.mockMvc.perform(post("/recipe/register/ajax")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(recipeJson)
                .with(csrf()));

        // When
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString("\"id\":1")))
                .andExpect(content().string(containsString("\"title\":\"레시피\"")))
                .andExpect(content().string(containsString("\"image\":\"recipe.jpg\"")))
                .andExpect(content().string(containsString("\"estimatedTime\":30")))
                .andExpect(content().string(containsString("\"difficulty\":1")));
    }

    @Test
    @WithMockUser
    public void When_다른_사용자_레시피_수정_페이지_조회_Then_정상_리턴() throws Exception {

        // Given
        given(this.recipeService.readRecipe(eq(this.recipe.getId()))).willReturn(this.recipeView);
        given(this.recipeService.readMaterialList()).willReturn(this.materialList);

        // When
        final ResultActions actions = this.mockMvc.perform(get("/recipe/modify/" + this.recipe.getId()));

        // Then
        actions.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    @WithMockUser
    public void When_레시피_수정_Ajax_Then_정상_리턴() throws Exception {

        // Given
        String recipeJson = this.objectMapper.writeValueAsString(this.recipe);
        given(this.recipeService.updateRecipe(eq(this.recipe.getId()), any(RecipeRequest.class), any(Integer.class))).willReturn(this.recipe);

        // When
        final ResultActions actions = this.mockMvc.perform(post("/recipe/modify/ajax/" + this.recipe.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(recipeJson)
                .with(csrf()));

        // Then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString("\"id\":1")))
                .andExpect(content().string(containsString("\"title\":\"레시피\"")))
                .andExpect(content().string(containsString("\"image\":\"recipe.jpg\"")))
                .andExpect(content().string(containsString("\"estimatedTime\":30")))
                .andExpect(content().string(containsString("\"difficulty\":1")));
    }

    @Test
    @WithMockUser
    public void When_레시피_삭제_호출_Then_정상_리턴() throws Exception {

        // When
        final ResultActions actions = this.mockMvc.perform(post("/recipe/delete/" + this.recipe.getId())
                .with(csrf()));

        // Then
        actions.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
}