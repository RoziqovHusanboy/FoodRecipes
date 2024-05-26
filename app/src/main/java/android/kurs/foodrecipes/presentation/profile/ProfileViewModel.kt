package android.kurs.foodrecipes.presentation.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import tj.tajsoft.domain.repository.ProfileSaveRepo
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
   private val _phoneNumber = MutableLiveData<String?>()
    val phoneNumber get() = _phoneNumber
  private  val _fullName = MutableLiveData<String>()
    val fullName get() = _fullName
    private val _location = MutableLiveData<String>()
    val location get() = _location
    private val _birthDay = MutableLiveData<String>()
    val birthDay get() = _birthDay
  private  val _profileImage = MutableLiveData<Bitmap?>()
    val profileImage get() = _profileImage

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
        _phoneNumber.postValue(repo.getPhoneNumber())
    }

    fun getFullName() = launchWithCatch {
        _fullName.postValue(repo.getFullName())
    }

    fun getLocation() = launchWithCatch {
        _location.postValue(repo.getLocation())
    }

    fun getBirthDay() = launchWithCatch {
        _birthDay.postValue(repo.getBirthDay()?:"")
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
            _profileImage.postValue(decodeImage(imageString.toString()))
        } else {
            _profileImage.postValue(null)
        }
    }

    private fun decodeImage(imageString: String): Bitmap {
        val byteArray = Base64.decode(imageString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}
