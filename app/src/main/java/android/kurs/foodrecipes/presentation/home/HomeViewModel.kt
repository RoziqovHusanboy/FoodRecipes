package android.kurs.foodrecipes.presentation.home

import tj.tajsoft.domain.model.network.home.get_home.BannerEntity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tj.tajsoft.domain.model.network.category.CategoryEntity
import tj.tajsoft.domain.repository.HomeRepository
import tj.tajsoft.domain.repository.MealRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mealRepository: MealRepository,
    private val homeRepository: HomeRepository
) : ViewModel() {

    private var category = MutableLiveData< List<CategoryEntity>>()
    private var home = MutableLiveData<List<BannerEntity>>( )
    val _category get() = category
    val _home get() = home

    val loading = MutableLiveData(false)
    val error = MutableLiveData(false)

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
     }

    fun getCategory() = launchWithCatch {
            category.postValue(mealRepository.getCategory())
        Log.d("TAG", "getCategory: ${mealRepository.getCategory()}")
    }

    private fun getHome() = launchWithCatch {
        home.postValue(homeRepository.getHome())
    }
}
