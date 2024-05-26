package tj.tajsoft.domain.repository

import tj.tajsoft.domain.model.network.category.CategoryEntity
import tj.tajsoft.domain.model.network.filterByArea.FilterByAreaEntity

interface MealRepository {
    suspend fun getCategory(): List<CategoryEntity>
    suspend fun getFoodFilterByArea(title:String):List<FilterByAreaEntity>
    suspend fun setOnboardingTrue()
    suspend fun getOnBoarding():Boolean?


}