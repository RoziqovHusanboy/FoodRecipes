package tj.tajsoft.domain.usecase.home

import tj.tajsoft.domain.model.network.home.get_home.BannerEntity
import tj.tajsoft.domain.repository.HomeRepository

class GetHomeUseCase(private val homeRepository: HomeRepository) {
   suspend  operator fun invoke():List<BannerEntity>{
     return   homeRepository.getHome()
    }
}