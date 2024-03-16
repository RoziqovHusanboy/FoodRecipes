package android.kurs.foodrecipes.domain.repo

import android.kurs.foodrecipes.data.model.Category

interface CategoryRepository {
    suspend fun getCategory(): Category
}