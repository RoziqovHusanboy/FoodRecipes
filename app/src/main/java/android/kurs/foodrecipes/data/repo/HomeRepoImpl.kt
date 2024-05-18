package android.kurs.foodrecipes.data.repo

import android.kurs.foodrecipes.data.api.home.HomeApi
import android.kurs.foodrecipes.data.model.home.ResponseHome
import android.kurs.foodrecipes.domain.repo.HomeRepository
import javax.inject.Inject

class HomeRepoImpl @Inject constructor (
    private val api: HomeApi
):HomeRepository {
    override suspend fun getHome(): ResponseHome {
       return  api.getHome()
    }
}