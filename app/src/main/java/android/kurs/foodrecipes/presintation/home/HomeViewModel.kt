package android.kurs.foodrecipes.presintation.home

import android.kurs.foodrecipes.data.model.category.Category
import android.kurs.foodrecipes.data.model.home.ResponseHome
import android.kurs.foodrecipes.domain.repo.CategoryRepository
import android.kurs.foodrecipes.domain.repo.HomeRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    init {
        getHome()
        getCategory()

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
        }catch (e:Exception){
            error.postValue(true)
        }finally {
            loading.postValue(false)
        }
    }


}