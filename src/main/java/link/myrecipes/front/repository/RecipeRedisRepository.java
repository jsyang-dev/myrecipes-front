package link.myrecipes.front.repository;

import link.myrecipes.front.dto.view.RecipeView;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRedisRepository extends CrudRepository<RecipeView, Integer> {
}
