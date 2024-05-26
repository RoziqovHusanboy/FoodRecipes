package tj.tajsoft.data.model.home.get_category


import com.google.gson.annotations.SerializedName

data class Product(
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