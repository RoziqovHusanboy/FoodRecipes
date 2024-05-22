package tj.tajsoft.data.api.meal

import retrofit2.http.GET
import retrofit2.http.Query
import tj.tajsoft.domain.model.network.category.Category
import tj.tajsoft.domain.model.network.filterByArea.ResponseFilterByArea

interface MealApi {
    @GET("categories.php")
    suspend fun getCategory(): Category

    @GET("filter.php")
    suspend fun getFoodBy(
        @Query("a") a: String
    ): ResponseFilterByArea
}