package tj.tajsoft.data.local.store

import tj.tajsoft.domain.model.local.FoodAddModel
import javax.inject.Inject

class FoodStore @Inject constructor(): BaseStore<Array<FoodAddModel>>("food", Array< FoodAddModel>::class.java) {
}