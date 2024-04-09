package android.kurs.foodrecipes.data.model.filterByArea


import com.google.gson.annotations.SerializedName

data class ResponseFilterByArea(
    @SerializedName("meals")
    val meals: ArrayList<Meal>
)