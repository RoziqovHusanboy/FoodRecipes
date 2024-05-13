package android.kurs.foodrecipes.presintation.profile

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri

class SharedPreferencesProfile(context: Context, nameShared:String) {
    private val sharedPreferences: SharedPreferences? =
        context.getSharedPreferences(nameShared, Context.MODE_PRIVATE)

    fun save(key: String, value: String) {
        val editor = sharedPreferences?.edit()
        editor?.putString(key, value.toString())
        editor?.apply()
    }

    fun get(key: String): String {
        return sharedPreferences?.getString(key, "") ?: ""
    }
}