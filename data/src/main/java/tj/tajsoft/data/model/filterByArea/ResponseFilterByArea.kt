package tj.tajsoft.data.model.filterByArea


import com.google.gson.annotations.SerializedName
import tj.tajsoft.domain.model.network.filterByArea.FilterByAreaEntity

data class ResponseFilterByArea(
    @SerializedName("meals")
    val meals: ArrayList<Meal>
)