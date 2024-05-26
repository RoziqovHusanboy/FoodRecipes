package android.kurs.foodrecipes.presentation.detail_home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tj.tajsoft.domain.model.network.category.CategoryEntity
import tj.tajsoft.domain.repository.MealRepository
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {
    private val _loading = MutableLiveData(false)
    val loading get() = _loading

    private val _error = MutableLiveData(false)
    val error get() = _error
    private val category = MutableLiveData< List<CategoryEntity>>()
    val _category get() = category

    init {

        getCategory()

    }


    fun getCategory() = viewModelScope.launch {
        _loading.postValue(true)
        _error.postValue(false)
        try {
            val responseList = repository.getCategory()
            category.postValue(responseList)
        } catch (e: Exception) {
            _error.postValue(true)
        } finally {
            _loading.postValue(true)
        }

    }
}