package android.kurs.foodrecipes.presintation.home

import android.kurs.foodrecipes.data.local.model.FoodAddModel
import android.kurs.foodrecipes.data.model.category.Category
import android.kurs.foodrecipes.data.model.home.get_home.ResponseHome
import android.kurs.foodrecipes.domain.repo.CategoryRepository
import android.kurs.foodrecipes.domain.repo.HomeRepository
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val homeRepository: HomeRepository
) : ViewModel() {

    private var category = MutableLiveData<Category>()
    private var home = MutableLiveData<ResponseHome>()
    val _category get() = category
    val _home get() = home

    val loading = MutableLiveData(false)
    val error = MutableLiveData(false)
    val _error = MutableLiveData<Exception>()
    val getFood = MutableLiveData<List<FoodAddModel?>>()

    init {
        getHome()
        getCategory()
        getFoodLocal()

    }

    fun getCategory() = viewModelScope.launch {
        loading.postValue(true)
        error.postValue(false)
        try {
            val category = categoryRepository.getCategory()
            this@HomeViewModel.category.postValue(category)

        } catch (e: Exception) {
            error.postValue(true)
            _error.postValue(e)
        } finally {
            loading.postValue(false)
        }

    }

    fun getHome() = viewModelScope.launch {
        loading.postValue(true)
        error.postValue(false)
        try {
            val response = homeRepository.getHome()
            home.postValue(response)
        } catch (e: Exception) {
            error.postValue(true)
        } finally {
            loading.postValue(false)
        }
    }

    private fun getFoodLocal() = viewModelScope.launch {
        withContext(NonCancellable) {
            try {
                getFood.postValue(categoryRepository.getLocalFoods()?.toList())
                Log.d("TAG", "getFoodLocal:${categoryRepository.getLocalFoods()?.toList()} ")
            } catch (e: Exception) {
                Log.d("TAG", "getFood:$e ")
            }
        }
    }
}