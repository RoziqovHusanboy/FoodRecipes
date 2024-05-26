package tj.tajsoft.data.remote.api.meal

import retrofit2.http.GET
import retrofit2.http.Query
import tj.tajsoft.data.model.category.CategoryModel
import tj.tajsoft.data.model.filterByArea.ResponseFilterByArea

interface MealApi {
    @GET("categories.php")
    suspend fun getCategory(): CategoryModel

    @GET("filter.php")
    suspend fun getFoodBy(
        @Query("a") a: String
    ): ResponseFilterByArea
}