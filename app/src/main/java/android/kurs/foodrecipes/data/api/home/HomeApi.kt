package android.kurs.foodrecipes.data.api.home

import android.kurs.foodrecipes.data.model.home.ResponseHome
import retrofit2.http.GET

interface HomeApi {
    @GET("home/get-home")
    suspend fun getHome():ResponseHome
}