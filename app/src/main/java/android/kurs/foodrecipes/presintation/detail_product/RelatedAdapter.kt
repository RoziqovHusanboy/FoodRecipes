package android.kurs.foodrecipes.presintation.detail_product

import android.kurs.foodrecipes.data.model.home.get_category.Related
import android.kurs.foodrecipes.databinding.ItemProductDetailRelatedBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RelatedAdapter(
    private val products: List<Related>,

) : RecyclerView.Adapter<RelatedAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemProductDetailRelatedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Related) = with(binding) {
            Glide.with(root).load(product.image).into(image)
            price.text = "$${product.price}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemProductDetailRelatedBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )


    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

}