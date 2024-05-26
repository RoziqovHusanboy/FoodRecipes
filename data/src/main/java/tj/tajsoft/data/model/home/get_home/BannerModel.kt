package tj.tajsoft.data.model.home.get_home


import com.google.gson.annotations.SerializedName

data class BannerModel(
    @SerializedName("banners")
    val banners: List<Banner>
)