package tj.tajsoft.data.remote.api.home

import retrofit2.http.GET
import tj.tajsoft.data.model.home.get_category.ProductModel
 import tj.tajsoft.data.model.home.get_home.BannerModel

interface HomeApi {
    @GET("home/get-home")
    suspend fun getHome(): BannerModel
    @GET("home/get-product")
    suspend fun getProduct(): ProductModel
}