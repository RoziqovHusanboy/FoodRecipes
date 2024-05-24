package tj.tajsoft.domain.repo

import android.location.Location
import tj.tajsoft.domain.model.local.FoodAddModel
import tj.tajsoft.domain.model.network.category.Category
import tj.tajsoft.domain.model.network.filterByArea.ResponseFilterByArea

interface MealRepository {
    suspend fun getCategory(): Category?
    suspend fun getFoodFilterByArea(title:String): ResponseFilterByArea
    suspend fun saveNewFood(foodAddModel: FoodAddModel)
    suspend fun getLocalFoods(): Array<FoodAddModel>?
    suspend fun onboarding()
    suspend fun getOnBoarding():Boolean?


}