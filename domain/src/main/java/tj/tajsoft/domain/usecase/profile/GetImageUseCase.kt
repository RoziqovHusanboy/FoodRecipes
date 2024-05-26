package tj.tajsoft.domain.usecase.profile

import tj.tajsoft.domain.repository.ProfileSaveRepo

class GetImageUseCase(private val repo: ProfileSaveRepo) {
    suspend operator fun invoke():String{
        return repo.getImage()
    }

}