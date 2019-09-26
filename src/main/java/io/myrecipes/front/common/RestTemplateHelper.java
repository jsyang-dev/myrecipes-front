package io.myrecipes.front.common;

import java.util.List;

public interface RestTemplateHelper {
    <T> T getForObject(Class<T> clazz, String url, Object... uriVariables);

    <T> List<T> getForList(Class<T> clazz, String url, Object... uriVariables);
}
