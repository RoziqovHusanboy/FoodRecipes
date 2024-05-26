package tj.tajsoft.domain.usecase.home

import tj.tajsoft.domain.model.network.home.product.ProductEntity
import tj.tajsoft.domain.repository.HomeRepository

class GetProductUseCase(private val homeRepository: HomeRepository) {
    suspend operator fun invoke():List<ProductEntity>{
        return homeRepository.getProduct()
    }
}