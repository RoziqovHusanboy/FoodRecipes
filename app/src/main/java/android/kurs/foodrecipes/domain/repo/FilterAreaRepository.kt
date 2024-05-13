package android.kurs.foodrecipes.domain.repo

import android.kurs.foodrecipes.data.model.filterByArea.ResponseFilterByArea

interface FilterAreaRepository {
    suspend fun getFoodFilterByArea(title:String):ResponseFilterByArea
}