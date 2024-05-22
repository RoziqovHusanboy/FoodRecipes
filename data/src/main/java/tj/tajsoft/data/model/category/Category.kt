package tj.tajsoft.data.model.category


import com.google.gson.annotations.SerializedName
import tj.tajsoft.domain.model.network.category.CategoryX

data class Category(
    @SerializedName("categories")
    val categories: List<CategoryX>
)