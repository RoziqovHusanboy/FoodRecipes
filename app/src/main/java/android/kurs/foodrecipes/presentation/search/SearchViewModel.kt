package android.kurs.foodrecipes.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tj.tajsoft.domain.model.network.filterByArea.FilterByAreaEntity
import tj.tajsoft.domain.repository.MealRepository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: MealRepository
) : ViewModel() {

    private val _filterFoods = MutableLiveData<List<FilterByAreaEntity>>()
    val filterFoods get() = _filterFoods
    private var _loading = MutableLiveData(false)
    val loading get() = _loading

    private var _error = MutableLiveData(false)
    val error get() = _error


    init {
        getFilteringFoods("American")
    }

      fun getFilteringFoods(title: String) = viewModelScope.launch {

        _loading.postValue(true)
        try {
            val response = repo.getFoodFilterByArea(title)
            _filterFoods.postValue(response)
        } catch (e: Exception) {
            _loading.postValue(false)
            _error.postValue(true)
        } finally {
            _loading.postValue(false)
        }

    }


}