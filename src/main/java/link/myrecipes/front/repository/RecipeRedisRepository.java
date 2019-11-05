package link.myrecipes.front.repository;

import link.myrecipes.front.domain.RecipeRedis;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRedisRepository extends CrudRepository<RecipeRedis, Integer> {
}
