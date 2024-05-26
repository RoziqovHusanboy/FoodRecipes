package tj.tajsoft.data.model.category


import com.google.gson.annotations.SerializedName

data class CategoryModel(
    @SerializedName("categories")
    val categories: List<Category>
)