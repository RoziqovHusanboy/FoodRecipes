package tj.tajsoft.domain.usecase.profile

import tj.tajsoft.domain.repository.ProfileSaveRepo

class SavePhoneNumberUseCase(private val profileSaveRepo: ProfileSaveRepo) {
    suspend operator fun invoke(phone: String) {
        profileSaveRepo.savePhoneNumber(phone)
    }
}