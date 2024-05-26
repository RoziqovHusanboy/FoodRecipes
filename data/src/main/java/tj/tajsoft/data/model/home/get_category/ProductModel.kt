package tj.tajsoft.data.model.home.get_category


import com.google.gson.annotations.SerializedName

data class ProductModel(
    @SerializedName("product")
    val product: List<Product>
)