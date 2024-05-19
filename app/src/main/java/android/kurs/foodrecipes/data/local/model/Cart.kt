package android.kurs.foodrecipes.data.local.model

import com.google.gson.annotations.SerializedName

data class Cart(
    val id:String,
    val title:String,
    val image:String,
     val price:Double,
    var count:Int
)
