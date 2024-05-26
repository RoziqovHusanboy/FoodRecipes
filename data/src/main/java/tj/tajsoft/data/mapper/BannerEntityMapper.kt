package tj.tajsoft.data.mapper

import tj.tajsoft.data.model.home.get_home.BannerModel
import tj.tajsoft.domain.model.network.home.get_home.BannerEntity

class BannerEntityMapper:(BannerModel)->List<BannerEntity> {
    override fun invoke(banner: BannerModel): List<BannerEntity>  {
       return banner.banners.map {
            BannerEntity(
                image = it.image
            )
        }
    }

}