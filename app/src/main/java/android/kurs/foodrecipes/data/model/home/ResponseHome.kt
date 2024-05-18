package android.kurs.foodrecipes.data.model.home


import com.google.gson.annotations.SerializedName

data class ResponseHome(
    @SerializedName("banners")
    val banners: List<Banner>
)