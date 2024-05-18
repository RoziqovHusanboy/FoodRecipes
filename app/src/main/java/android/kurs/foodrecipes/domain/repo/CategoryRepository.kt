package android.kurs.foodrecipes.domain.repo

import android.kurs.foodrecipes.data.local.model.FoodAddModel
import android.kurs.foodrecipes.data.model.category.Category

interface CategoryRepository {
    suspend fun getCategory(): Category
    suspend fun saveNewFood(foodAddModel: FoodAddModel)
    suspend fun getLocalFoods():FoodAddModel?
}