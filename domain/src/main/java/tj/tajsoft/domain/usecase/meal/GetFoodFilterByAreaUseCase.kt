package tj.tajsoft.domain.usecase.meal

import tj.tajsoft.domain.model.network.category.CategoryEntity
import tj.tajsoft.domain.model.network.filterByArea.FilterByAreaEntity
import tj.tajsoft.domain.repository.MealRepository

class GetFoodFilterByAreaUseCase(private val mealRepository: MealRepository) {
    suspend operator fun invoke(title:String):List<FilterByAreaEntity>{
        return mealRepository.getFoodFilterByArea(title)
    }
}