package tj.tajsoft.data.mapper

import tj.tajsoft.data.model.filterByArea.ResponseFilterByArea
import tj.tajsoft.domain.model.network.filterByArea.FilterByAreaEntity

class FilterByAreaMapper:(ResponseFilterByArea)->List<FilterByAreaEntity> {
    override fun invoke(filter: ResponseFilterByArea): List<FilterByAreaEntity> {
         return filter.meals.map {
             FilterByAreaEntity(
                 idMeal = it.idMeal,
                 strMeal = it.strMeal,
                 strMealThumb = it.strMealThumb
             )
         }
    }
}