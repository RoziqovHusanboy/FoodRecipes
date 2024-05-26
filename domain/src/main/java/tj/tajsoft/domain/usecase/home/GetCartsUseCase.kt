package tj.tajsoft.domain.usecase.home

import kotlinx.coroutines.flow.Flow
import tj.tajsoft.domain.model.local.Cart
import tj.tajsoft.domain.repository.HomeRepository

class GetCartsUseCase(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(): Flow<List<Cart>>{
        return homeRepository.getCarts()
    }
}