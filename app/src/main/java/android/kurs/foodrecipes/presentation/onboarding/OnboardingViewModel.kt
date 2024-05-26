package android.kurs.foodrecipes.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tj.tajsoft.domain.repository.MealRepository
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val repo: MealRepository
):ViewModel() {
    fun onboarding() =viewModelScope.launch {
        repo.setOnboardingTrue()
    }


}

