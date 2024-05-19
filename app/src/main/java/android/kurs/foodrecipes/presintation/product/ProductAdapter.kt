package android.kurs.foodrecipes.presintation.product

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.kurs.foodrecipes.data.model.home.get_category.ResponseProducts
import android.kurs.foodrecipes.data.model.home.get_category.ResponseProductsItem
import android.kurs.foodrecipes.databinding.ItemProductBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class ProductAdapter(
    val list:MutableList<ResponseProductsItem>,
    val onclick:(id:String)->Unit
):RecyclerView.Adapter<ProductAdapter.ViewHolderProduct>() {

    inner class ViewHolderProduct(val binding:ItemProductBinding):RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(item: ResponseProductsItem) = with(binding){
            Glide.with(binding.root).load(item.image).listener(object:RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.loading.isVisible =false
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.loading.isVisible =false
                    return false
                }

            }).into(image)
            name.text = item.title
            desc.text = item.desc
            price.text = "$${item.price}"
            root.setOnClickListener{
                onclick(item.id)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolderProduct(
        ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolderProduct, position: Int) {
        holder.bind(list[position])
    }


}