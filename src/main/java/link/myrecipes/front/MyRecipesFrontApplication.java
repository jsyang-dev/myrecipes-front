package link.myrecipes.front;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableCaching
@EnableAspectJAutoProxy
public class MyRecipesFrontApplication {
    private static final String PROPERTIES_LOCATIONS = "spring.config.location="
            + "classpath:/application.yml,"
            + "classpath:/aws.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(MyRecipesFrontApplication.class)
                .properties(PROPERTIES_LOCATIONS)
                .run(args);
    }

}
