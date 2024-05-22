package tj.tajsoft.domain.repo

import android.content.Context
import kotlinx.coroutines.flow.Flow
import tj.tajsoft.domain.model.local.Cart
import tj.tajsoft.domain.model.local.FoodAddModel
import tj.tajsoft.domain.model.network.home.get_category.ResponseProducts
import tj.tajsoft.domain.model.network.home.get_home.ResponseHome

interface HomeRepository {
    suspend fun getHome(): ResponseHome
    suspend fun getProduct(): ResponseProducts
    suspend fun addToCard(cart: Cart)
    fun getCarts(): Flow<List<Cart>>
    suspend fun clearCart()
 }