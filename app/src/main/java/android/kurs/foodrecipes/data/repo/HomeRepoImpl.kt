package android.kurs.foodrecipes.data.repo

import android.kurs.foodrecipes.data.api.home.HomeApi
import android.kurs.foodrecipes.data.local.model.Cart
import android.kurs.foodrecipes.data.local.store.CartStore
import android.kurs.foodrecipes.data.model.home.get_category.ResponseProducts
import android.kurs.foodrecipes.data.model.home.get_home.ResponseHome
import android.kurs.foodrecipes.domain.repo.HomeRepository
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepoImpl @Inject constructor (
    private val api: HomeApi,
    private val cartStore: CartStore
):HomeRepository {
    override suspend fun getHome(): ResponseHome {
        return api.getHome()
    }

    override suspend fun getProduct(): ResponseProducts {
        return api.getProduct()
    }

    override suspend fun addToCard(cart: Cart) {
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

    override fun getCarts(): Flow<List<Cart>> {
       return cartStore.getFlow().map { it?.toList()?: emptyList() }
    }

    override suspend fun clearCart() {
        return cartStore.clear()
    }

}