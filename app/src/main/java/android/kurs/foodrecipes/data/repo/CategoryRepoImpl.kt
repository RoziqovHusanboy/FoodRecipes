package android.kurs.foodrecipes.data.repo

import android.kurs.foodrecipes.data.api.categories.Api
import android.kurs.foodrecipes.data.local.model.FoodAddModel
import android.kurs.foodrecipes.data.local.store.FoodAddStore
import android.kurs.foodrecipes.data.model.category.Category
import android.kurs.foodrecipes.domain.repo.CategoryRepository
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class CategoryRepoImpl @Inject constructor(
    private val categoryApi: Api,
    private val foodAddStore: FoodAddStore
) : CategoryRepository {
    override suspend fun getCategory(): Category = categoryApi.getCategory()
    override suspend fun saveNewFood(foodAddModel: FoodAddModel) {
        val foodStore = (foodAddStore.get() ?: emptyArray())
            .toList()
            .filterNot { it.name == foodAddModel.name }
            .toMutableList()
        foodAddStore.set(foodStore.toTypedArray())
        Log.d("TAG", "saveNewFood: saved to local")
    }

    override suspend fun getLocalFoods(): Array<FoodAddModel>? {
        return foodAddStore.get()
    }
}