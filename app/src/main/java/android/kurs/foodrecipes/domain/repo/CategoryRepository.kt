package android.kurs.foodrecipes.domain.repo

import android.kurs.foodrecipes.data.local.model.FoodAddModel
import android.kurs.foodrecipes.data.model.category.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategory(): Category
    suspend fun saveNewFood(foodAddModel: FoodAddModel)
    suspend fun getLocalFoods(): Array<FoodAddModel>?
}