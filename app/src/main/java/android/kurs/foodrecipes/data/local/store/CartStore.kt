package android.kurs.foodrecipes.data.local.store

import android.kurs.foodrecipes.data.local.model.Cart
import javax.inject.Inject

class CartStore @Inject constructor():BaseStore<Array<Cart>>("cart", Array<Cart>::class.java) {
}