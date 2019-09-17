package io.myrecipes.front.service;

import io.myrecipes.front.domain.PageParam;
import io.myrecipes.front.domain.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    private RestServiceImpl restService;

    @Value("${app.index.page-size}")
    private int pageSize;

    @Value("${app.index.sort-field}")
    private String sortField;

    @Value("${app.index.is-descending}")
    private boolean isDescending;

    @Test
    public void Should_페이지_정상_반환_When_페이지_조회() {
        Recipe recipe = new Recipe("test1", "test1.jpg", 30, "1", 1001);
        List<Recipe> list = Collections.singletonList(recipe);
        ResponseEntity<List<Recipe>> responseEntity = new ResponseEntity<>(list, HttpStatus.OK);
        PageParam pageParam = new PageParam(0, this.pageSize, this.sortField, this.isDescending);
        given(this.restService.<List<Recipe>>callApi(argThat(new IndexServiceImplTest.UrlMatcher(pageParam)), eq(HttpMethod.GET))).willReturn(responseEntity);

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