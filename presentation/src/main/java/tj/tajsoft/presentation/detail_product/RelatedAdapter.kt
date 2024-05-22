package android.kurs.foodrecipes.presintation.detail_product

import tj.tajsoft.domain.model.network.home.get_category.Related
import android.kurs.foodrecipes.databinding.ItemProductDetailRelatedBinding
import tj.tajsoft.domain.repo.OnClickRelatedInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RelatedAdapter(
    private val products: List<Related>,
    val onClickRelatedInterface: OnClickRelatedInterface

) : RecyclerView.Adapter<RelatedAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemProductDetailRelatedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Related) = with(binding) {
            Glide.with(root).load(product.image).into(image)
            price.text = "$${product.price}"
            binding.root.setOnClickListener {
            onClickRelatedInterface.onClick(drawable = product.image)
            }
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