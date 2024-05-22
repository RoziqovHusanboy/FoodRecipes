package android.kurs.foodrecipes.utils

import android.kurs.foodrecipes.R


object AddingLatLng {
   suspend fun addingLocation(): ArrayList<Location> {

       val location = arrayListOf(
           Location( 40.269911,69.6360747,"Ресторан Бахтиёр", R.drawable.restorant_bahtiyor),
           Location(40.2689343,69.6368272,"Кафе Анора", R.drawable.kafe_anora),
           Location(40.2681478,69.6376091,"Кафе Нодира", R.drawable.kafe_nodira),
           Location(40.2714319,69.64248,"Шаҳди Хуҷанд", R.drawable.shahdi_hujand),
           Location(40.27961,69.6275662,"TajBurger", R.drawable.tajburger),
           Location(40.2883277,69.6173489,"Кафе Темурмалик", R.drawable.kafe_temurmalik),
           Location(40.2878043,69.6184711,"Paprika", R.drawable.kafe_paprika),
           Location(40.2950703,69.6198956,"Кафе ВАКТ", R.drawable.kafe_vaqt),
           Location(40.2981801,69.6154331,"Лаззат тез тайёр", R.drawable.lazzat_tez_tayyor),
           Location(40.2966463,69.6127679,"Кафе Гулшан", R.drawable.kafe_gulshan),
           Location(40.2864202,69.612442,"Кафе Равшан", R.drawable.kafe_ravshan),
           Location(40.2974444,69.6089727,"Насими Баҳор", R.drawable.nasimi_bahor)


       )
       return location
     }
}

data class Location (val latitute:Double,val longitute:Double,val title:String,val drawable:Int)