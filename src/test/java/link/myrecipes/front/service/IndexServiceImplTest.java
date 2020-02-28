package link.myrecipes.front.service;

import link.myrecipes.front.common.RestTemplateHelperImpl;
import link.myrecipes.front.dto.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
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

    @Test
    public void When_메인_페이지_조회_Then_페이지_정상_반환() {
        // Given
        Recipe recipe = Recipe.builder().title("test1").image("image1.jpg").estimatedTime(30).difficulty(1).build();
        List<Recipe> list = Collections.singletonList(recipe);
        PageRequest pageRequest = PageRequest.of(0, 9, Sort.Direction.DESC, "registerDate");

        given(this.restTemplateHelper.getForList(eq(Recipe.class), contains("/recipes"))).willReturn(list);

        // When
        final List<Recipe> recipeList = this.indexService.readRecipeList(pageRequest);

        // Then
        assertThat(recipeList.size(), is(1));
        assertThat(recipeList.get(0).equals(recipe), is(true));
    }
}