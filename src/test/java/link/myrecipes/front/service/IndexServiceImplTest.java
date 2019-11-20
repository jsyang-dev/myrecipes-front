package link.myrecipes.front.service;

import link.myrecipes.front.common.RestTemplateHelperImpl;
import link.myrecipes.front.dto.PageParam;
import link.myrecipes.front.dto.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class IndexServiceImplTest {
    @InjectMocks
    private IndexServiceImpl indexService;

    @Mock
    private RestTemplateHelperImpl restTemplateHelper;

    @Value("${app.index.page-size}")
    private int pageSize;

    @Value("${app.index.sort-field}")
    private String sortField;

    @Value("${app.index.is-descending}")
    private boolean isDescending;

    @Test
    public void When_메인_페이지_조회_Then_페이지_정상_반환() {
        //given
        Recipe recipe = Recipe.builder().title("test1").image("image1.jpg").estimatedTime(30).difficulty(1).build();
        List<Recipe> list = Collections.singletonList(recipe);
        PageParam pageParam = PageParam.builder().page(0).size(this.pageSize).sortField(this.sortField).isDescending(this.isDescending).build();

        given(this.restTemplateHelper.getForList(eq(Recipe.class), contains("/recipes"))).willReturn(list);

        //when
        final List<Recipe> recipeList = this.indexService.readRecipeList(pageParam);

        //then
        assertThat(recipeList.size(), is(1));
        assertThat(recipeList.get(0).equals(recipe), is(true));
    }
}