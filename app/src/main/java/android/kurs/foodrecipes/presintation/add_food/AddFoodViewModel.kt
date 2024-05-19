package android.kurs.foodrecipes.presintation.add_food

import android.kurs.foodrecipes.data.local.model.FoodAddModel
import android.kurs.foodrecipes.domain.repo.CategoryRepository
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class AddFoodViewModel @Inject constructor(
    private val repo: CategoryRepository
) : ViewModel() {



    fun saveFood(foodAddModel: FoodAddModel) = viewModelScope.launch {
        withContext(NonCancellable) {
            try {
                repo.saveNewFood(foodAddModel)
                Log.d("TAG", "saveFood:saved ")
            } catch (e: Exception) {
                Log.d("TAG", "saveFood: $e")
            }
        }
    }



}