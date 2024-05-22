package tj.tajsoft.data.local.store

import tj.tajsoft.domain.model.local.Cart
import javax.inject.Inject

class CartStore @Inject constructor(): BaseStore<Array<Cart>>("cart", Array<Cart>::class.java) {
}