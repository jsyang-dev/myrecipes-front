package io.myrecipes.front.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestServiceImplTest {
    @Autowired
    private RestServiceImpl restService;

    @Value("${app.api.recipe}")
    private String api;

    @Test
    public void 레시피_API_호출_테스트() {
        String url = api + "/health";
        ResponseEntity<String> responseEntity = restService.callApi(url, HttpMethod.GET);

        assertThat(responseEntity.getBody(), is("Hello System"));
    }
}