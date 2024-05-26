package tj.tajsoft.domain.repository

import kotlinx.coroutines.flow.Flow
import tj.tajsoft.domain.model.local.Cart
import tj.tajsoft.domain.model.network.home.get_home.BannerEntity
import tj.tajsoft.domain.model.network.home.product.ProductEntity

interface HomeRepository {
    suspend fun getHome(): List<BannerEntity>
    suspend fun getProduct(): List<ProductEntity>
    suspend fun addToCard(cart: Cart)
    fun getCarts(): Flow<List<Cart>>
    suspend fun clearCart()
 }