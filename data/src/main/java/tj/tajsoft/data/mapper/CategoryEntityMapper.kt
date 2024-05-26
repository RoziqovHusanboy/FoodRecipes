package tj.tajsoft.data.mapper

import tj.tajsoft.data.model.category.CategoryModel
import tj.tajsoft.domain.model.network.category.CategoryEntity

class CategoryEntityMapper:(CategoryModel)->List<CategoryEntity> {
    override fun invoke(category:CategoryModel): List<CategoryEntity> {
        return category.categories.map {
                CategoryEntity(
                    idCategory = it.idCategory,
                    strCategory = it.strCategory,
                    strCategoryDescription = it.strCategoryDescription,
                    strCategoryThumb = it.strCategoryThumb
                )
        }
    }

}