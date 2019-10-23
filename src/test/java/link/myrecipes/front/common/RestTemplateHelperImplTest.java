package link.myrecipes.front.common;

import link.myrecipes.front.dto.Recipe;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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

    @Value("${app.api.recipe.scheme}")
    private String scheme;

    @Value("${app.api.recipe.host}")
    private String host;

    @Value("${app.api.recipe.port}")
    private String port;

    @Test
    @Ignore
    public void API_호출_테스트() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(this.scheme)
                .host(this.host)
                .port(this.port)
                .path("/health")
                .build(true);

        Object recipe = restTemplateHelper.getForEntity(Recipe.class, uriComponents.toUriString());

        assertThat(recipe, instanceOf(Recipe.class));
    }
}