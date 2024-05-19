package android.kurs.foodrecipes.data.api.home

import android.kurs.foodrecipes.data.model.home.get_category.ResponseProducts
import android.kurs.foodrecipes.data.model.home.get_home.ResponseHome
import retrofit2.http.GET

interface HomeApi {
    @GET("home/get-home")
    suspend fun getHome(): ResponseHome
    @GET("home/get-product")
    suspend fun getProduct(): ResponseProducts
}