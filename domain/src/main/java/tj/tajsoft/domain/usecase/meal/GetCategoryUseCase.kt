package tj.tajsoft.domain.usecase.meal

import tj.tajsoft.domain.model.network.category.CategoryEntity
import tj.tajsoft.domain.repository.MealRepository

class GetCategoryUseCase(private val mealRepository: MealRepository) {
    suspend operator fun invoke():List<CategoryEntity>{
        return mealRepository.getCategory()
    }
}