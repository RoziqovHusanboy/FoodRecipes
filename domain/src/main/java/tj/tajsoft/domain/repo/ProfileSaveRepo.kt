package tj.tajsoft.domain.repo

import android.net.Uri

interface ProfileSaveRepo {
    suspend fun savePhoneNumber(phone:String)
    suspend fun saveFullName(fullName:String)
    suspend fun saveLocation(location:String)
    suspend fun saveBirthDay(birth:String)
    suspend fun saveImage(image:String)
    suspend fun getImage():String
    suspend fun getPhoneNumber():String
    suspend fun getFullName():String
    suspend fun getLocation():String
    suspend fun getBirthDay():String
}