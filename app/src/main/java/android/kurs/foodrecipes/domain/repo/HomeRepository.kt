package android.kurs.foodrecipes.domain.repo

import android.kurs.foodrecipes.data.model.home.ResponseHome

interface HomeRepository {
    suspend fun getHome():ResponseHome
}