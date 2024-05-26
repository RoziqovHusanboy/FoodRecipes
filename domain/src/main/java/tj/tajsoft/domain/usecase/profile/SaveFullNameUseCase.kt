package tj.tajsoft.domain.usecase.profile

import tj.tajsoft.domain.repository.ProfileSaveRepo

class SaveFullNameUseCase(private val profileSaveRepo: ProfileSaveRepo) {
    suspend operator fun invoke(fullName:String){
        profileSaveRepo.saveFullName(fullName)
    }

}