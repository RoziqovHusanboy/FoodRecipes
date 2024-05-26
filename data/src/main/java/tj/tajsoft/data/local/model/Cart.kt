package tj.tajsoft.data.local.model


data class Cart(
    val id:String,
    val title:String,
    val image:String,
     val price:Double,
    var count:Int
)
