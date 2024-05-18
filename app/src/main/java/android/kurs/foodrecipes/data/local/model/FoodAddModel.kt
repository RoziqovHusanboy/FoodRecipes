package android.kurs.foodrecipes.data.local.model

import android.net.Uri

data class FoodAddModel(
    val name:String,
    val category:String,
    val desc:String,
    val url:Uri
)