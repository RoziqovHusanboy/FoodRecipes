package tj.tajsoft.domain.usecase.meal

import tj.tajsoft.domain.model.network.category.CategoryEntity
import tj.tajsoft.domain.repository.MealRepository

class SetOnboardingTrueUseCase(private val mealRepository: MealRepository) {
    suspend operator fun invoke(){
        return mealRepository.setOnboardingTrue()
    }
}