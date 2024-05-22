package tj.tajsoft.data.model.home.get_home


import com.google.gson.annotations.SerializedName
import tj.tajsoft.domain.model.network.home.get_home.Banner

data class ResponseHome(
    @SerializedName("banners")
    val banners: List<Banner>
)