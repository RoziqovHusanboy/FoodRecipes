package tj.tajsoft.data.repo

import android.annotation.SuppressLint
import tj.tajsoft.domain.model.local.FoodAddModel
import tj.tajsoft.data.local.store.FoodStore
import tj.tajsoft.data.local.store.onBoardingStore
import tj.tajsoft.domain.model.network.category.Category
import tj.tajsoft.domain.model.network.filterByArea.ResponseFilterByArea
import android.util.Log
import tj.tajsoft.data.api.meal.MealApi
import tj.tajsoft.domain.repo.MealRepository
import javax.inject.Inject

class MealRepoImpl @Inject constructor(
    private val mealApi: MealApi,
    private val foodAddStore: FoodStore,
    private val onBoardingStore: onBoardingStore
) : MealRepository {
    override suspend fun getCategory(): Category = mealApi.getCategory()
    override suspend fun getFoodFilterByArea(title: String): ResponseFilterByArea {
        return mealApi.getFoodBy(title)
    }

    override suspend fun saveNewFood(foodAddModel:FoodAddModel ) {
        Log.d("TAG", "saveNewFoodFirst: $foodAddModel")
        val foodStore = (foodAddStore.get() ?: emptyArray())
            .toList()
            .filterNot { it.name == foodAddModel.name }
            .toMutableList()
        foodAddStore.set(foodStore.toTypedArray())
        Log.d("TAG", "saveNewFood: saved to local")
    }

    @SuppressLint("SuspiciousIndentation")
    override suspend fun getLocalFoods(): Array<FoodAddModel>? {
      val response =  foodAddStore.get()
        Log.d("getFoodLocal", "getFoodLocal: $response")
        return response
    }

    override suspend fun onboarding() {
        return onBoardingStore.set(true)
    }

    override suspend fun getOnBoarding():Boolean? {
       val response =  onBoardingStore.get()
        Log.d("TAG", "getOnBoarding: $response")
        return response
    }



}