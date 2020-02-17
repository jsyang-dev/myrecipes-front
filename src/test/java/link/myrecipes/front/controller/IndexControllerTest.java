package link.myrecipes.front.controller;

import link.myrecipes.front.dto.PageParam;
import link.myrecipes.front.service.IndexService;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;

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

//    @Test
//    public void 메인_페이지_호출() throws Exception {
//        //given
//        Recipe recipe = Recipe.builder().title("test1").image("image1.jpg").estimatedTime(30).difficulty(1).build();
//        PageParam pageParam = PageParam.builder().page(1).size(this.pageSize).sortField(this.sortField).isDescending(this.isDescending).build();
//
//        given(indexService.readPopularRecipeList()).willReturn(Collections.singletonList(recipe));
//        given(this.indexService.readRecipeList(argThat(new PageParamMatcher(pageParam)))).willReturn(Collections.singletonList(recipe));
//        given(this.indexService.readRecipePageCount()).willReturn(1);
//
//        //when
//        final ResultActions actions = this.mockMvc.perform(get("/index"));
//
//        //then
//        actions.andExpect(status().isOk())
//                .andExpect(view().name("index"))
//                .andExpect(model().attributeExists("popularRecipeList", "newRecipeList", "recipePageCount", "recipeImagePath"))
//                .andExpect(model().attribute("popularRecipeList", contains(recipe)))
//                .andExpect(model().attribute("newRecipeList", contains(recipe)))
//                .andExpect(model().attribute("recipePageCount", 1))
//                .andExpect(model().attribute("recipeImagePath", this.recipeImagePath));
//    }

    static class PageParamMatcher implements ArgumentMatcher<PageParam> {
        private PageParam left;

        PageParamMatcher(PageParam left) {
            this.left = left;
        }

        @Override
        public boolean matches(PageParam right) {
            return left.getPage() == right.getPage()
                    && left.getSize() == right.getSize()
                    && left.getSortField().equals(right.getSortField())
                    && left.isDescending() == right.isDescending();
        }
    }
}