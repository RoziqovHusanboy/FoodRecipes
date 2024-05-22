package android.kurs.foodrecipes.presentation.detail_product

import tj.tajsoft.domain.model.local.Cart
import tj.tajsoft.domain.model.network.home.get_category.ResponseProducts
 import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tj.tajsoft.domain.repo.HomeRepository
import javax.inject.Inject

@HiltViewModel
class DetailProductViewModel @Inject constructor(
    private val repo: HomeRepository
)
    :ViewModel() {

    private var products = MutableLiveData<ResponseProducts>()
    val _product get() = products
    val loading = MutableLiveData(false)
    val error = MutableLiveData(false)
    val _error = MutableLiveData<Exception>()
    val count = MutableLiveData(1)

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

    fun increment(){
        var current:Int = count.value?:1
        current++
        count.postValue(current)

    }
    fun decrement(){
        var current:Int = count.value?:1
        if (current==1) return
        current--
        count.postValue(current)

    }

    fun set(id:String,title:String,image: String,price:Double) = viewModelScope.launch{
        val cart = Cart(
            id = id,
            title = title,
            image = image,
            price = price,
            count = count.value!!
        )
        repo.addToCard(cart)
    }

}