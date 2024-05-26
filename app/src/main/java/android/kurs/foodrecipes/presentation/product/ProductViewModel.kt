package android.kurs.foodrecipes.presentation.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tj.tajsoft.domain.model.network.home.product.ProductEntity
import tj.tajsoft.domain.repository.HomeRepository
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
 private val repo: HomeRepository
):ViewModel() {
    private var products = MutableLiveData< List<ProductEntity>>()
    val _product get() = products
   private val _loading = MutableLiveData(false)
    val loading get() = _loading

    private val _error = MutableLiveData(false)
    val error get() = _error

    init {
        getHomeCategory()
    }
    private fun getHomeCategory() = viewModelScope.launch {
        _loading.postValue(true)
        _error.postValue(false)
        try {
            val response = repo.getProduct()
            products.postValue(response)
        }catch (e:Exception){
            _error.postValue(true)
        }finally {
            _loading.postValue(false)
        }
    }




}