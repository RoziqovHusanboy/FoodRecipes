package tj.tajsoft.data.api.home

import tj.tajsoft.domain.model.network.home.get_category.ResponseProducts
import tj.tajsoft.domain.model.network.home.get_home.ResponseHome
import retrofit2.http.GET

interface HomeApi {
    @GET("home/get-home")
    suspend fun getHome(): ResponseHome
    @GET("home/get-product")
    suspend fun getProduct(): ResponseProducts
}