package io.myrecipes.front.common;

import io.myrecipes.front.dto.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.config.location="
        + "classpath:/application.yml,"
        + "classpath:/aws.yml"
)
public class RestTemplateHelperImplTest {
    @Autowired
    private RestTemplateHelperImpl restTemplateHelper;

    @Value("${app.api.recipe}")
    private String api;

    @Test
    public void API_호출_테스트() {
        String url = api + "/health";
        Object recipe = restTemplateHelper.getForObject(Recipe.class, url);

        assertThat(recipe, instanceOf(Recipe.class));
    }
}