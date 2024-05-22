package android.kurs.foodrecipes.presintation.detail_home

import tj.tajsoft.domain.model.network.category.Category
import android.kurs.foodrecipes.domain.repo.MealRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {
    val loading = MutableLiveData(false)
    val error = MutableLiveData(false)
    private val category = MutableLiveData<Category>()
    val _category get() = category
    init {

    getCategory()

    }


    fun getCategory() = viewModelScope.launch {
        loading.postValue(true)
        error.postValue(false)
        try {
            val responseList = repository.getCategory()
            category.postValue(responseList)
        } catch (e: Exception) {
            error.postValue(true)
        } finally {
            loading.postValue(true)
        }

    }
}