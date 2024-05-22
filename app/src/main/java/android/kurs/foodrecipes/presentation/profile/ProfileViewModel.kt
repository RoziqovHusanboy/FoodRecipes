package android.kurs.foodrecipes.presentation.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import tj.tajsoft.domain.repo.ProfileSaveRepo
import android.util.Base64
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: ProfileSaveRepo
):ViewModel() {
    val phoneNumber = MutableLiveData<String>()
    val fullName = MutableLiveData<String>()
    val location = MutableLiveData<String>()
    val birthDay = MutableLiveData<String>()
    val profileImage = MutableLiveData<Bitmap?>()

    init {
        getBirthDay()
        getLocation()
        getFullName()
        getPhoneNumber()
        loadProfileImage()
    }
    private fun logError(action: String, e: Exception) {
        Log.d("ProfileViewModel", "$action: $e")
    }

    private fun <T> launchWithCatch(action: suspend () -> T) = viewModelScope.launch {
        try {
            action()
        } catch (e: Exception) {
            logError("Exception in $action", e)
        }
    }

    ///////  <----------- save data to store  -------->  /////////
    fun savePhoneNumber(phone: String) = launchWithCatch {
        repo.savePhoneNumber(phone)
    }

    fun saveFullName(fullName: String) = launchWithCatch {
        repo.saveFullName(fullName)
    }

    fun saveLocation(location: String) = launchWithCatch {
        repo.saveLocation(location)
    }

    fun saveBirthDay(birth: String) = launchWithCatch {
        repo.saveBirthDay(birth)
    }

    ///////  <----------- get data from store  -------->  /////////
    private fun getPhoneNumber() = launchWithCatch {
        phoneNumber.postValue(repo.getPhoneNumber())
    }

    fun getFullName() = launchWithCatch {
        fullName.postValue(repo.getFullName())
    }

    fun getLocation() = launchWithCatch {
        location.postValue(repo.getLocation())
    }

    fun getBirthDay() = launchWithCatch {
        birthDay.postValue(repo.getBirthDay())
    }

    ///////  <----------- work with gallery -------->  /////////
    fun saveImageToSharedPreferences(bitmap: Bitmap) = launchWithCatch {
        val imageString = encodeImage(bitmap)
        repo.saveImage(imageString.toUri().toString())

    }

    private fun encodeImage(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun loadProfileImage() = launchWithCatch {
        val imageString = repo.getImage()
        if (!imageString.isNullOrEmpty()) {
            profileImage.postValue(decodeImage(imageString.toString()))
        } else {
            profileImage.postValue(null)
        }
    }

    private fun decodeImage(imageString: String): Bitmap {
        val byteArray = Base64.decode(imageString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}
