package android.kurs.foodrecipes.data.repo

import android.kurs.foodrecipes.data.api.categories.Api
import android.kurs.foodrecipes.data.model.category.Category
import android.kurs.foodrecipes.domain.repo.CategoryRepository
import javax.inject.Inject

class CategoryRepoImpl @Inject constructor(
    private val categoryApi: Api
):CategoryRepository {
    override suspend fun getCategory(): Category = categoryApi.getCategory()
}