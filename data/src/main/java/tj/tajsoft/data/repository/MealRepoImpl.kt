package tj.tajsoft.data.repository

import tj.tajsoft.data.local.store.onBoardingStore
import android.util.Log
import tj.tajsoft.data.mapper.CategoryEntityMapper
import tj.tajsoft.data.mapper.FilterByAreaMapper
import tj.tajsoft.data.remote.api.meal.MealApi
import tj.tajsoft.domain.model.network.category.CategoryEntity
import tj.tajsoft.domain.model.network.filterByArea.FilterByAreaEntity
import tj.tajsoft.domain.repository.MealRepository
import javax.inject.Inject

class MealRepoImpl @Inject constructor(
    private val mealApi: MealApi,
     private val onBoardingStore: onBoardingStore
) : MealRepository {
    override suspend fun getCategory(): List<CategoryEntity>{
        val response = mealApi.getCategory()
        Log.d("TAG", "getCategory: $response")
        return CategoryEntityMapper().invoke(response)
    }

    override suspend fun getFoodFilterByArea(title: String): List<FilterByAreaEntity> {
        val response = mealApi.getFoodBy(title)
        return FilterByAreaMapper().invoke(response)

    }
    override suspend fun setOnboardingTrue() {
        return onBoardingStore.set(true)
    }

    override suspend fun getOnBoarding(): Boolean? {
        val response = onBoardingStore.get()
        Log.d("TAG", "getOnBoarding: $response")
        return response
    }


}