package tj.tajsoft.domain.model.network.home.product


data class ProductEntity(
    val desc: String,
    val id: String,
    val image: String,
    val name: String,
    val price: Int,
    val related: List<RelatedEntity>,
    val title: String
)