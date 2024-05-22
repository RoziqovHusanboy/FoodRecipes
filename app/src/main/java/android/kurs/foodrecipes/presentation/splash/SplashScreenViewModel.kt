package android.kurs.foodrecipes.presentation.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tj.tajsoft.domain.repo.MealRepository
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val repo: MealRepository
) : ViewModel() {
    val getOnboarded = MutableLiveData<Boolean>()
    init {
        getOnbording()
    }
    private fun getOnbording() = viewModelScope.launch {
        try {
            getOnboarded.postValue(repo.getOnBoarding())
        } catch (e: Exception) {
            Log.d("TAG", "getOnbording:$e ")
        }
    }
}