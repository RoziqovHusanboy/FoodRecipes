package android.kurs.foodrecipes.data.api.categories

import android.kurs.foodrecipes.data.model.category.Category
import android.kurs.foodrecipes.data.model.filterByArea.ResponseFilterByArea
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {
    @GET("categories.php")
    suspend fun getCategory(): Category

    @GET("filter.php")
    suspend fun getFoodBy(
        @Query("a") a: String
    ): ResponseFilterByArea

}