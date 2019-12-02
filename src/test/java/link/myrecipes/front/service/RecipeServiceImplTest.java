package link.myrecipes.front.service;

import link.myrecipes.front.common.RestTemplateHelperImpl;
import link.myrecipes.front.dto.Material;
import link.myrecipes.front.dto.Recipe;
import link.myrecipes.front.dto.request.RecipeRequest;
import link.myrecipes.front.dto.view.RecipeView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceImplTest {
    private Recipe recipe;
    private RecipeView recipeView;
    private RecipeRequest recipeRequest;
    private List<Material> materialList;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Mock
    private RestTemplateHelperImpl restTemplateHelper;

    @Before
    public void setUp() {
        this.recipe = Recipe.builder()
                .id(1)
                .title("레시피")
                .image("recipe.jpg")
                .estimatedTime(30)
                .difficulty(1)
                .build();

        this.recipeView = RecipeView.builder()
                .id(this.recipe.getId())
                .title(this.recipe.getTitle())
                .image(this.recipe.getImage())
                .estimatedTime(this.recipe.getEstimatedTime())
                .difficulty(this.recipe.getDifficulty())
                .build();

        this.recipeRequest = RecipeRequest.builder()
                .title(this.recipe.getTitle())
                .image(this.recipe.getImage())
                .estimatedTime(this.recipe.getEstimatedTime())
                .difficulty(this.recipe.getDifficulty())
                .build();

        this.materialList = new ArrayList<>();
        this.materialList.add(Material.builder()
                .id(10)
                .name("재료")
                .unitName("kg")
                .build());
    }

    @Test
    public void When_레시피_조회_Then_정상_반환() {
        //given
        given(this.restTemplateHelper.getForEntity(eq(RecipeView.class), contains("/recipes"))).willReturn(this.recipeView);

        //when
        final RecipeView selectedRecipeView = this.recipeService.readRecipe(this.recipeView.getId());

        //then
        assertThat(selectedRecipeView, instanceOf(RecipeView.class));
        assertThat(selectedRecipeView.getId(), is(this.recipeView.getId()));
        assertThat(selectedRecipeView.getTitle(), is(this.recipeView.getTitle()));
        assertThat(selectedRecipeView.getImage(), is(this.recipeView.getImage()));
        assertThat(selectedRecipeView.getEstimatedTime(), is(this.recipeView.getEstimatedTime()));
        assertThat(selectedRecipeView.getDifficulty(), is(this.recipeView.getDifficulty()));
    }

    @Test
    public void When_재료_조회_Then_정상_반환() {
        //given
        given(this.restTemplateHelper.getForList(eq(Material.class), contains("/materials"))).willReturn(this.materialList);

        //when
        final List<Material> selectedMaterialList = this.recipeService.readMaterialList();

        //then
        assertThat(selectedMaterialList.size(), is(1));
        assertThat(selectedMaterialList.get(0), instanceOf(Material.class));
        assertThat(selectedMaterialList.get(0).getId(), is(this.materialList.get(0).getId()));
        assertThat(selectedMaterialList.get(0).getName(), is(this.materialList.get(0).getName()));
        assertThat(selectedMaterialList.get(0).getUnitName(), is(this.materialList.get(0).getUnitName()));
    }

    @Test
    public void When_레시피_저장_Then_정상_반환() {
        //given
        given(this.restTemplateHelper.postForEntity(eq(Recipe.class), contains("/recipes"), any(RecipeRequest.class))).willReturn(this.recipe);

        //when
        final Recipe selectedRecipe = this.recipeService.createRecipe(this.recipeRequest);

        //then
        assertThat(selectedRecipe, instanceOf(Recipe.class));
        assertThat(selectedRecipe.getId(), is(this.recipe.getId()));
        assertThat(selectedRecipe.getTitle(), is(this.recipe.getTitle()));
        assertThat(selectedRecipe.getImage(), is(this.recipe.getImage()));
        assertThat(selectedRecipe.getEstimatedTime(), is(this.recipe.getEstimatedTime()));
        assertThat(selectedRecipe.getDifficulty(), is(this.recipe.getDifficulty()));
    }

    @Test
    public void When_레시피_수정_Then_정상_반환() {
        //given
        given(this.restTemplateHelper.putForEntity(eq(Recipe.class), contains("/recipes"), any(RecipeRequest.class))).willReturn(this.recipe);

        //when
        final Recipe selectedRecipe = this.recipeService.updateRecipe(this.recipe.getId(), this.recipeRequest);

        //then
        assertThat(selectedRecipe, instanceOf(Recipe.class));
        assertThat(selectedRecipe.getId(), is(this.recipeView.getId()));
        assertThat(selectedRecipe.getTitle(), is(this.recipeView.getTitle()));
        assertThat(selectedRecipe.getImage(), is(this.recipeView.getImage()));
        assertThat(selectedRecipe.getEstimatedTime(), is(this.recipeView.getEstimatedTime()));
        assertThat(selectedRecipe.getDifficulty(), is(this.recipeView.getDifficulty()));
    }

    @Test
    public void When_레시피_삭제_Then_정상_반환() {
        //when
        this.recipeService.deleteRecipe(this.recipeView.getId());

        //then
        verify((this.restTemplateHelper), times(1)).delete(any(String.class));
    }
}