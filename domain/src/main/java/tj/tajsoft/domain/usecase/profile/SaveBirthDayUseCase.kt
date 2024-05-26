package tj.tajsoft.domain.usecase.profile

import tj.tajsoft.domain.repository.ProfileSaveRepo

class SaveBirthDayUseCase(private val profileSaveRepo: ProfileSaveRepo) {
    suspend operator fun invoke(birth:String){
        profileSaveRepo.saveBirthDay(birth)
    }

}