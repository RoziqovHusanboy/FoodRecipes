package android.kurs.foodrecipes.data.repo

import android.kurs.foodrecipes.data.api.categories.CategoryApi
import android.kurs.foodrecipes.data.model.Category
import android.kurs.foodrecipes.domain.repo.CategoryRepository
import javax.inject.Inject

class CategoryRepoImpl @Inject constructor(
    private val categoryApi: CategoryApi
):CategoryRepository {
    override suspend fun getCategory(): Category = categoryApi.getCategory()

}