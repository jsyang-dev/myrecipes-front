package link.myrecipes.front.controller;

import link.myrecipes.front.dto.Recipe;
import link.myrecipes.front.service.IndexService;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.hamcrest.Matchers.contains;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class IndexControllerTest extends ControllerTest {

    @MockBean
    private IndexService indexService;

    @Value("${app.image-path.recipe}")
    private String recipeImagePath;

    @Value("${app.index.page-size}")
    private int pageSize;

    @Value("${app.index.sort-field}")
    private String sortField;

    @Value("${app.index.is-descending}")
    private boolean isDescending;

    @Test
    public void 메인_페이지_호출() throws Exception {

        // Given
        Sort.Direction direction;
        if (isDescending) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }
        PageRequest pageRequest = PageRequest.of(0, this.pageSize, direction,this.sortField);
        Recipe recipe = Recipe.builder().title("test1").image("image1.jpg").estimatedTime(30).difficulty(1).build();

        given(this.indexService.readPopularRecipeList()).willReturn(Collections.singletonList(recipe));
        given(this.indexService.readRecipeList(argThat(new PageRequestMatcher(pageRequest)))).willReturn(Collections.singletonList(recipe));
        given(this.indexService.readRecipePageCount()).willReturn(1);

        // When
        final ResultActions actions = this.mockMvc.perform(get("/index"));

        // Then
        actions.andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("popularRecipeList", "newRecipeList", "recipePageCount", "recipeImagePath"))
                .andExpect(model().attribute("popularRecipeList", contains(recipe)))
                .andExpect(model().attribute("newRecipeList", contains(recipe)))
                .andExpect(model().attribute("recipePageCount", 1))
                .andExpect(model().attribute("recipeImagePath", this.recipeImagePath));
    }

    static class PageRequestMatcher implements ArgumentMatcher<PageRequest> {

        private PageRequest left;

        PageRequestMatcher(PageRequest left) {
            this.left = left;
        }

        @Override
        public boolean matches(PageRequest right) {
            return left.getPageNumber() == right.getPageNumber()
                    && left.getPageSize() == right.getPageSize()
                    && left.getSort().equals(right.getSort());
        }
    }
}