package tj.tajsoft.data.mapper

 import tj.tajsoft.data.model.home.get_category.ProductModel
 import tj.tajsoft.domain.model.network.home.product.ProductEntity

class ProductEntityMapper:(ProductModel)->List<ProductEntity> {
    override fun invoke(product: ProductModel): List<ProductEntity> {
        return product.product.map {
            ProductEntity(
                desc = it.desc,
                id = it.id,
                image = it.image,
                name = it.name,
                price = it.price,
                related = RelatedEntityMapper().invoke(it.related),
                title = it.title
            )
        }
    }
}