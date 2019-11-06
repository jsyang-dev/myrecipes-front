package link.myrecipes.front.common;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface RedisTemplateHelper<T> {
    T getValue(String key);

    List<T> getListValue(String key);

    void setValue(String key, T val, long timeout, TimeUnit timeUnit);

    void setList(String key, List<T> list, long timeout, TimeUnit timeUnit);

    void delValue(String key);

    String makeKey(Class<T> clazz, String id);
}
