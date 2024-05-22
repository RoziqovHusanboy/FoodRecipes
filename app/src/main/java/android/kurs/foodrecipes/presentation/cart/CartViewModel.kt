package android.kurs.foodrecipes.presentation.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tj.tajsoft.domain.model.local.Cart
import tj.tajsoft.domain.repo.HomeRepository
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repo: HomeRepository
) : ViewModel() {

    val carts = MutableLiveData<List<Cart>?>()
    val loading = MutableLiveData(false)
    init {
        getCarts()
    }


    private fun getCarts() = viewModelScope.launch {
        repo.getCarts().collectLatest {
            carts.postValue(it)
        }

    }

    fun set(cart: Cart) = viewModelScope.launch {
        repo.addToCard(cart)
    }

    fun clear() = viewModelScope.launch {
        repo.clearCart()
    }

}