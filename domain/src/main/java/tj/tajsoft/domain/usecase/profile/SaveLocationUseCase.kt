package tj.tajsoft.domain.usecase.profile

import tj.tajsoft.domain.repository.ProfileSaveRepo

class SaveLocationUseCase(private val profileSaveRepo: ProfileSaveRepo) {
    suspend operator fun invoke(location:String){
        profileSaveRepo.saveLocation(location)
    }

}