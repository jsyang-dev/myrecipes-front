package link.myrecipes.front.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisTemplateHelperImpl<T> implements RedisTemplateHelper<T> {
    private final RedisTemplate redisTemplate;

    public RedisTemplateHelperImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public T getValue(String key) {
        log.info("Redis getValue ===> key: " + key);
        T result = null;

        try {
            ValueOperations valueOperations = redisTemplate.opsForValue();
            result = (T) valueOperations.get(key);
        } catch (Exception e) {
            log.info("Redis getValue fail: ", e);
        }

        return result;
    }

    @Override
    public List<T> getListValue(String key) {
        return null;
    }

    @Override
    public void setValue(String key, T value, long timeout, TimeUnit timeUnit) {
        try {
            log.info("Redis putValue ===> key: " + key);
            ValueOperations valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key, value, timeout, timeUnit);
        } catch (Exception e) {
            log.info("Redis putValue fail: ", e);
        }
    }

    @Override
    public void setList(String key, List<T> list, long timeout, TimeUnit timeUnit) {

    }

    @Override
    public void delValue(String key) {

    }

    @Override
    public String makeKey(Class<T> clazz, String id) {
        return clazz.getName() + ":" + id;
    }
}
