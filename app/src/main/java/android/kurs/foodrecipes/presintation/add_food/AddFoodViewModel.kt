package android.kurs.foodrecipes.presintation.add_food

import android.kurs.foodrecipes.data.local.model.FoodAddModel
import android.kurs.foodrecipes.domain.repo.CategoryRepository
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFoodViewModel @Inject constructor(
    private val repo: CategoryRepository
) : ViewModel() {
    val getFood = MutableLiveData<FoodAddModel>()

    init {
        getFoodLocal()
    }

    fun saveFood(foodAddModel: FoodAddModel) = viewModelScope.launch {
        try {
            repo.saveNewFood(foodAddModel)
        } catch (e: Exception) {
            Log.d("TAG", "saveFood: $e")
        }
    }

    private fun getFoodLocal() = viewModelScope.launch {
        try {
            getFood.postValue(repo.getLocalFoods())
        } catch (e: Exception) {
            Log.d("TAG", "getFood:$e ")
        }
    }

}