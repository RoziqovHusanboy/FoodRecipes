package android.kurs.foodrecipes.domain.repo

import android.kurs.foodrecipes.data.model.category.Category

interface CategoryRepository {
    suspend fun getCategory(): Category
}