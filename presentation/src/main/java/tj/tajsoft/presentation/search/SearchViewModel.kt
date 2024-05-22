package android.kurs.foodrecipes.presintation.search

import tj.tajsoft.domain.model.network.filterByArea.ResponseFilterByArea
import android.kurs.foodrecipes.domain.repo.MealRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: MealRepository
) : ViewModel() {

    private val _filterFoods = MutableLiveData<ResponseFilterByArea>()
    val filterFoods get() = _filterFoods
      var loading = MutableLiveData(false)
      var error = MutableLiveData(false)

    init {
        getFilteringFoods("American")
    }

      fun getFilteringFoods(title: String) = viewModelScope.launch {

        loading.postValue(true)
        try {
            val response = repo.getFoodFilterByArea(title)
            _filterFoods.postValue(response)
        } catch (e: Exception) {
            loading.postValue(false)
            error.postValue(true)
        } finally {
            loading.postValue(false)
        }

    }


}