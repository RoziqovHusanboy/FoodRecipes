package android.kurs.foodrecipes.presentation.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tj.tajsoft.domain.repository.MealRepository
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val repo: MealRepository
) : ViewModel() {
    private val _getOnboarded = MutableLiveData<Boolean>()
    val getOnboarded get() = _getOnboarded
    init {
        getOnbording()
    }
    private fun getOnbording() = viewModelScope.launch {
        try {
            _getOnboarded.postValue(repo.getOnBoarding())
        } catch (e: Exception) {
            Log.d("TAG", "getOnbording:$e ")
        }
    }
}