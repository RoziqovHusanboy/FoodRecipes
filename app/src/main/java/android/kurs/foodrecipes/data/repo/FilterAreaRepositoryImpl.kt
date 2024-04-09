package android.kurs.foodrecipes.data.repo

import android.kurs.foodrecipes.data.api.categories.Api
import android.kurs.foodrecipes.data.model.filterByArea.ResponseFilterByArea
import android.kurs.foodrecipes.domain.repo.FilterAreaRepository
import javax.inject.Inject

class FilterAreaRepositoryImpl @Inject constructor(
    private val api: Api
) : FilterAreaRepository {
    override suspend fun getFoodFilterByArea(title: String): ResponseFilterByArea {
        return api.getFoodBy(title)
    }

}