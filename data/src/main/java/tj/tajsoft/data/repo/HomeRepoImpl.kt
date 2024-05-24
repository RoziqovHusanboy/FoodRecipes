package tj.tajsoft.data.repo

import android.content.Context
import tj.tajsoft.data.api.home.HomeApi

import tj.tajsoft.data.local.store.CartStore
import tj.tajsoft.domain.model.network.home.get_category.ResponseProducts
import tj.tajsoft.domain.model.network.home.get_home.ResponseHome
 import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
 import tj.tajsoft.domain.repo.HomeRepository

import javax.inject.Inject

class HomeRepoImpl @Inject constructor (
    private val api: HomeApi,
    private val cartStore: CartStore
): HomeRepository {
    override suspend fun getHome(): ResponseHome {
        return api.getHome()
    }

    override suspend fun getProduct(): ResponseProducts {
        return api.getProduct()
    }

    override suspend fun addToCard(cart: tj.tajsoft.domain.model.local.Cart) {
        val carts = (cartStore.get() ?: emptyArray())
            .toList()
            .filterNot { it.id == cart.id }
            .toMutableList()
        if (cart.count > 0) {
            carts.add(cart)
        }
        cartStore.set(carts.toTypedArray())
        Log.d("addtoCart", "addToCard: added to card")
    }

    override fun getCarts(): Flow<List<tj.tajsoft.domain.model.local.Cart>> {
       return cartStore.getFlow().map { it?.toList()?: emptyList() }
    }

    override suspend fun clearCart() {
        return cartStore.clear()
    }


}