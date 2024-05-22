package android.kurs.foodrecipes.presintation.onboarding

import android.kurs.foodrecipes.domain.repo.MealRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val repo:MealRepository
):ViewModel() {
    fun onboarding() =viewModelScope.launch {
        repo.onboarding()
    }


}

