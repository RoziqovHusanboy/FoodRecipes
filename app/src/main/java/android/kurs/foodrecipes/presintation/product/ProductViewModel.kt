package android.kurs.foodrecipes.presintation.product

import android.kurs.foodrecipes.data.model.home.get_category.ResponseProducts
import android.kurs.foodrecipes.domain.repo.HomeRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
 private val repo:HomeRepository
):ViewModel() {
    private var products = MutableLiveData<ResponseProducts>()
    val _product get() = products
    val loading = MutableLiveData(false)
    val error = MutableLiveData(false)
    val _error = MutableLiveData<Exception>()

    init {
        getHomeCategory()
    }
    private fun getHomeCategory() = viewModelScope.launch {
        loading.postValue(true)
        error.postValue(false)
        try {
            val response = repo.getProduct()
            products.postValue(response)
        }catch (e:Exception){
            error.postValue(true)
        }finally {
            loading.postValue(false)
        }
    }




}