package android.kurs.foodrecipes.presintation.map

import android.kurs.foodrecipes.domain.model.AddingLatLng
import android.kurs.foodrecipes.domain.model.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {
    private val _location = MutableLiveData<ArrayList<Location>>()
    val location get() = _location

    init {
        getLocationFoods()
    }

    private fun getLocationFoods() = viewModelScope.launch {
        try {
            _location.postValue(AddingLatLng.addingLocation())
        } catch (e: Exception) {
            Log.d("TAG", "getLocationFoods:$e ")
        }
    }
}
