package android.kurs.foodrecipes.presintation.home

import android.content.Context
import tj.tajsoft.data.local.model.FoodAddModel
import tj.tajsoft.data.local.store.FoodAddNewStore
import tj.tajsoft.domain.model.network.category.Category
import tj.tajsoft.domain.model.network.home.get_home.ResponseHome
import android.kurs.foodrecipes.domain.repo.MealRepository
import android.kurs.foodrecipes.domain.repo.HomeRepository
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mealRepository: MealRepository,
    private val homeRepository: HomeRepository
) : ViewModel() {

    private var category = MutableLiveData<Category>()
    private var home = MutableLiveData<ResponseHome>()
    val _category get() = category
    val _home get() = home

    val loading = MutableLiveData(false)
    val error = MutableLiveData(false)
    val getFood = MutableLiveData<List<FoodAddModel?>>()
    val getNewFood = MutableLiveData<MutableList<FoodAddModel?>?>()

    private fun logError(action: String, e: Exception) {
        Log.d("ProfileViewModel", "$action: $e")
    }

    private fun <T> launchWithCatch(action: suspend () -> T) = viewModelScope.launch {
        loading.postValue(true)
        error.postValue(false)
        try {
            action()
            error.postValue(false)
        } catch (e: Exception) {
            logError("Exception in $action", e)
            error.postValue(true)
        } finally {
            loading.postValue(false)
        }
    }

    init {
        getHome()
        getCategory()
        getFoodLocal()
    }

    fun getCategory() = launchWithCatch {
        category.postValue(mealRepository.getCategory())
    }

    private fun getHome() = launchWithCatch {
        home.postValue(homeRepository.getHome())
    }

    private fun getFoodLocal() = launchWithCatch {
        withContext(NonCancellable) {
            getFood.postValue(mealRepository.getLocalFoods()?.toList())
        }
    }

    fun getNewFood(context: Context) = launchWithCatch {
        val foodAddStore = FoodAddNewStore()
        getNewFood.postValue(foodAddStore.getFoodList(context, "new_food"))
    }

    fun removeByPositionFromFoodStore(context: Context, position: Int) {
        val foodAddStore = FoodAddNewStore()
        try {
            foodAddStore.removeFoodByPosition(context, "new_food", position)
        } catch (e: Exception) {
            Log.d("TAG", "saveNewFood: $e")
        }
    }
}
