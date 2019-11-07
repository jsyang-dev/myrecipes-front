package link.myrecipes.front.common;

import java.util.List;

public interface RestTemplateHelper {
    <T> List<T> getForList(Class<T> clazz, String url, Object... uriVariables);

    <T> T getForEntity(Class<T> clazz, String url, Object... uriVariables);

    <T, R> T postForEntity(Class<T> clazz, String url, R body, Object... uriVariables);

    <T, R> T putForEntity(Class<T> clazz, String url, R body, Object... uriVariables);

    void delete(String url);
}
