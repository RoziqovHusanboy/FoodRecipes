package android.kurs.foodrecipes.presintation.add_food

import android.content.Context
import tj.tajsoft.data.local.model.FoodAddModel
import tj.tajsoft.data.local.store.FoodAddNewStore
import android.kurs.foodrecipes.domain.repo.MealRepository
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddFoodViewModel @Inject constructor(
    private val repo: MealRepository
) : ViewModel() {
    fun saveNewFood(context: Context, food: FoodAddModel) {
        val foodAddStore = FoodAddNewStore()
        try {
            foodAddStore.addFoodToSharedPreferences(context, food, "new_food")
            Log.d("TAG", "saveNewFoodsaveNewFoodsaveNewFood: $food")
        } catch (e: Exception) {
            Log.d("TAG", "saveNewFood: $e")
        }
    }

}