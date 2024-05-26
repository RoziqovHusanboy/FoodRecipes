package tj.tajsoft.data.local.store

 import tj.tajsoft.data.local.model.Cart
 import javax.inject.Inject

class CartStore @Inject constructor(): BaseStore<Array<Cart>>("cart", Array<Cart>::class.java) {
}