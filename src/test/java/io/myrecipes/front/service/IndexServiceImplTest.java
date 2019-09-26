package io.myrecipes.front.service;

import io.myrecipes.front.common.RestTemplateHelperImpl;
import io.myrecipes.front.dto.PageParam;
import io.myrecipes.front.dto.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class IndexServiceImplTest {
    @InjectMocks
    private IndexServiceImpl indexService;

    @Mock
    private RestTemplateHelperImpl restService;

    @Mock
    private RestTemplate restTemplate;

    @Value("${app.index.page-size}")
    private int pageSize;

    @Value("${app.index.sort-field}")
    private String sortField;

    @Value("${app.index.is-descending}")
    private boolean isDescending;

    @Test
    public void Should_페이지_정상_반환_When_페이지_조회() {
        Recipe recipe = Recipe.builder().title("test1").image("image1.jpg").estimatedTime(30).difficulty(1).build();
        List<Recipe> list = Collections.singletonList(recipe);
        PageParam pageParam = PageParam.builder().page(0).size(this.pageSize).sortField(this.sortField).isDescending(this.isDescending).build();

        given(this.restService.getForList(eq(Recipe.class), argThat(new IndexServiceImplTest.UrlMatcher(pageParam)))).willReturn(list);

        final List<Recipe> recipeList = this.indexService.readRecipeList(pageParam);

        assertThat(recipeList.size(), is(1));
        assertThat(recipeList.get(0).equals(recipe), is(true));
    }

    static class UrlMatcher implements ArgumentMatcher<String> {
        private PageParam left;

        @Value("${app.api.recipe}")
        private String api;

        UrlMatcher(PageParam left) {
            this.left = left;
        }

        @Override
        public boolean matches(String right) {
            String url = api + "/recipes"
                    + "?page=" + left.getPage()
                    + "&size=" + left.getSize()
                    + "&sortField=" + left.getSortField()
                    + "&isDescending=" + left.isDescending();
            return url.equals(right);
        }
    }

}