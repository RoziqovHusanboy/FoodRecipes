package tj.tajsoft.domain.model.network.home.get_category


data class ResponseProductsItem(
    val desc: String,
    val id: String,
    val image: String,
    val name: String,
    val price: Int,
    val related: List<Related>,
    val title: String
)