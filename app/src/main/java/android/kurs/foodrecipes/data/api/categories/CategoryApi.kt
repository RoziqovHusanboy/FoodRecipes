package android.kurs.foodrecipes.data.api.categories

import android.kurs.foodrecipes.data.model.Category
import retrofit2.http.GET

interface CategoryApi {
//    https://www.themealdb.com/api/json/v1/1/categories.php
//    www.themealdb.com/api/json/v1/1/filter.php?c=fesh
//    www.themealdb.com/api/json/v1/1/filter.php?a=American

    @GET("categories.php")
    suspend fun getCategory():Category

}