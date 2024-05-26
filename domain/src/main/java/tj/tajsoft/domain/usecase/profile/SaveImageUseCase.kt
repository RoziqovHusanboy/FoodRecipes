package tj.tajsoft.domain.usecase.profile

import tj.tajsoft.domain.repository.ProfileSaveRepo

class SaveImageUseCase(private val profileSaveRepo: ProfileSaveRepo) {
    suspend operator fun invoke(image:String){
        profileSaveRepo.saveImage(image)
    }

}