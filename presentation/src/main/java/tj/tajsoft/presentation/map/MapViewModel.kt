package android.kurs.foodrecipes.presintation.map

import android.content.Context
import android.content.Intent
import tj.tajsoft.data.local.Location
import android.kurs.foodrecipes.domain.repo.MealRepository
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val  repo:MealRepository
) : ViewModel() {
    private val _location = MutableLiveData<ArrayList<Location>>()
    val location get() = _location

    init {
        getLocationFoods()
    }

    private fun getLocationFoods() = viewModelScope.launch {
        try {
            val response = repo.getAddingLatLng()
            _location.postValue(response)
        } catch (e: Exception) {
            Log.d("TAG", "getLocationFoods:$e ")
        }
    }

     fun drawPolyline(startLocation: android.location.Location, endLatLng: LatLng,context: Context):Intent {
        val gmmIntentUri =
            Uri.parse("https://www.google.com/maps/dir/?api=1&origin=${startLocation.latitude},${startLocation.longitude}&destination=${endLatLng.latitude},${endLatLng.longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        return mapIntent.setPackage("com.google.android.apps.maps")
    }

}
