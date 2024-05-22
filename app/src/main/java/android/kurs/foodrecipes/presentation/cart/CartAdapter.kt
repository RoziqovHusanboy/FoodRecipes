package android.kurs.foodrecipes.presentation.cart

import tj.tajsoft.domain.model.local.Cart
import android.kurs.foodrecipes.databinding.ItemCartBinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import tj.tajsoft.utils.dp

class CartAdapter(
    private val carts: List<Cart>,
    private val set: (cart: Cart) -> Unit
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cart: Cart) = with(binding) {
            Glide.with(root).load(cart.image)
                .transform(CenterCrop(),RoundedCorners(14.dp))
                .into(image)
            title.text = cart.title
            price.text = "$${cart.price}"
            count.text = cart.count.toString()


            plus.setOnClickListener {
                count.text = cart.count.toString()
                cart.count++
                set(cart)
            }
            minus.setOnClickListener {
                cart.count--
                count.text = cart.count.toString()
                set(cart)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = carts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(carts[position])
    }

}