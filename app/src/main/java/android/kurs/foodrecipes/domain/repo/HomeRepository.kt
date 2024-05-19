package android.kurs.foodrecipes.domain.repo

import android.kurs.foodrecipes.data.local.model.Cart
import android.kurs.foodrecipes.data.model.home.get_category.ResponseProducts
import android.kurs.foodrecipes.data.model.home.get_home.ResponseHome
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getHome(): ResponseHome
    suspend fun getProduct(): ResponseProducts
    suspend fun addToCard(cart: Cart)
    fun getCarts(): Flow<List<Cart>>
    suspend fun clearCart()
}