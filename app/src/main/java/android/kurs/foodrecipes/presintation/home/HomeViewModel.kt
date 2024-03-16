package android.kurs.foodrecipes.presintation.home

import android.kurs.foodrecipes.data.model.Category
import android.kurs.foodrecipes.domain.repo.CategoryRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private var category = MutableLiveData<Category>()
    val _category get() = category
    val loading = MutableLiveData(false)
    val error = MutableLiveData(false)
    val _error = MutableLiveData<Exception>()

    init {
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


}