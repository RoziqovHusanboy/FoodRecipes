package tj.tajsoft.data.model.home.get_category


import com.google.gson.annotations.SerializedName

data class Related(
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: String
)