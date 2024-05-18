package android.kurs.foodrecipes.data.repo

import android.kurs.foodrecipes.data.api.categories.Api
import android.kurs.foodrecipes.data.local.model.FoodAddModel
import android.kurs.foodrecipes.data.local.store.FoodAddStore
import android.kurs.foodrecipes.data.model.category.Category
import android.kurs.foodrecipes.domain.repo.CategoryRepository
import android.util.Log
import javax.inject.Inject

class CategoryRepoImpl @Inject constructor(
    private val categoryApi: Api,
    private val foodAddStore: FoodAddStore
) : CategoryRepository {
    override suspend fun getCategory(): Category = categoryApi.getCategory()
    override suspend fun saveNewFood(foodAddModel: FoodAddModel) {
        foodAddStore.set(foodAddModel)
        Log.d("TAG", "saveNewFood: saved to local")
    }

    override suspend fun getLocalFoods() = foodAddStore.get()
}