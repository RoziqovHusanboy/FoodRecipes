package tj.tajsoft.domain.usecase.home

import tj.tajsoft.domain.model.local.Cart
import tj.tajsoft.domain.repository.HomeRepository

class AddToCardUseCase(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(cart: Cart){
        return homeRepository.addToCard(cart)
    }
}