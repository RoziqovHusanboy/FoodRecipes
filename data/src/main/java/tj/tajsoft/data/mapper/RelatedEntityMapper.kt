package tj.tajsoft.data.mapper

import tj.tajsoft.data.model.home.get_category.Related
import tj.tajsoft.domain.model.network.home.product.RelatedEntity

class RelatedEntityMapper:(List<Related>)->List<RelatedEntity> {
    override fun invoke(related: List<Related>): List<RelatedEntity> {
        return related.map {
            RelatedEntity(
                image = it.image,
                price = it.price
            )
        }
    }
}