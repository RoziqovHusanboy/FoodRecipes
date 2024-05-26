package tj.tajsoft.domain.usecase.home

import tj.tajsoft.domain.repository.HomeRepository

class ClearCartUseCase(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(){
        return homeRepository.clearCart()
    }
}