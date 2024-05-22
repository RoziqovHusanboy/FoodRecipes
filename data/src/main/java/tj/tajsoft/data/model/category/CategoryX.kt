package tj.tajsoft.data.model.category


import com.google.gson.annotations.SerializedName

data class CategoryX(
    @SerializedName("idCategory")
    val idCategory: String,
    @SerializedName("strCategory")
    val strCategory: String,
    @SerializedName("strCategoryDescription")
    val strCategoryDescription: String,
    @SerializedName("strCategoryThumb")
    val strCategoryThumb: String
)