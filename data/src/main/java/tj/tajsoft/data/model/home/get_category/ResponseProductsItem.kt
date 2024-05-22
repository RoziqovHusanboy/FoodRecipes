package tj.tajsoft.data.model.home.get_category


import com.google.gson.annotations.SerializedName
import tj.tajsoft.domain.model.network.home.get_category.Related

data class ResponseProductsItem(
    @SerializedName("desc")
    val desc: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("related")
    val related: List<Related>,
    @SerializedName("title")
    val title: String
)