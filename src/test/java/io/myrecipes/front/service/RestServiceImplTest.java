package io.myrecipes.front.service;

import io.myrecipes.front.dto.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestServiceImplTest {
    @Autowired
    private RestServiceImpl restService;

    @Value("${app.api.recipe}")
    private String api;

    @Test
    public void API_호출_테스트() {
        String url = api + "/health";
        ResponseEntity<Recipe> responseEntity = restService.callApi(url, HttpMethod.GET);

        assertThat(responseEntity.getBody(), instanceOf(LinkedHashMap.class));
    }
}