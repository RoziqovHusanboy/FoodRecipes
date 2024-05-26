package tj.tajsoft.data.repository

import tj.tajsoft.data.remote.api.home.HomeApi

import tj.tajsoft.data.local.store.CartStore
import tj.tajsoft.domain.model.network.home.get_home.BannerEntity
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tj.tajsoft.data.mapper.BannerEntityMapper
import tj.tajsoft.data.mapper.ProductEntityMapper
import tj.tajsoft.domain.model.local.Cart
import tj.tajsoft.domain.model.network.home.product.ProductEntity
import tj.tajsoft.domain.repository.HomeRepository

import javax.inject.Inject

class HomeRepoImpl @Inject constructor(
    private val api: HomeApi,
    private val cartStore: CartStore
) : HomeRepository {
    override suspend fun getHome(): List<BannerEntity> {
        val response = api.getHome()
        return BannerEntityMapper().invoke(response)
    }

    override suspend fun getProduct(): List<ProductEntity> {
        val response = api.getProduct()
        return ProductEntityMapper().invoke(response)
    }

    override suspend fun addToCard(cart: Cart) {
        val carts = (cartStore.get() ?: emptyArray())
            .toList()
            .filterNot { it.id == cart.id }
            .toMutableList()

        val dataCart = tj.tajsoft.data.local.model.Cart(
            id = cart.id,
            title = cart.title,
            image = cart.image,
            price = cart.price,
            count = cart.count
        )
        if (cart.count > 0) {
            carts.add(dataCart)
        }
        cartStore.set(carts.toTypedArray())
        Log.d("addtoCart", "addToCard: added to card")
    }

    override fun getCarts(): Flow<List<Cart>> {

        val response = cartStore.getFlow().map {
            val listCart = it?.toList()
            listCart?.map {
                Cart(
                    id = it.id,
                    title = it.title,
                    image = it.image,
                    price = it.price,
                    count = it.count
                )
            } ?: emptyList()
        }
        return response
    }

    override suspend fun clearCart() {
        return cartStore.clear()
    }


}