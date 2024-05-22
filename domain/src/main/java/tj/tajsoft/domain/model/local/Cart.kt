package tj.tajsoft.domain.model.local


data class Cart(
    val id:String,
    val title:String,
    val image:String,
     val price:Double,
    var count:Int
)
