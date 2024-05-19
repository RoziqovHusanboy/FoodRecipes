package android.kurs.foodrecipes.presintation.home

import android.graphics.drawable.Drawable
import android.kurs.foodrecipes.data.local.model.FoodAddModel
import android.kurs.foodrecipes.databinding.ItemAddingFoodRcBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class ItemAddFoodAdapter(
    val list :List<FoodAddModel?>
):RecyclerView.Adapter<ItemAddFoodAdapter.ViewHolder>() {
    inner class ViewHolder(val binding:ItemAddingFoodRcBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:FoodAddModel){
            Glide.with(binding.root).load(item.url).listener(object :RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressbar.isVisible = false
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressbar.isVisible = false
                    return false
                }

            }).into(binding.image)
            binding.category.text = item.category
            binding.desc.text = item.desc
            binding.title.text = item.name

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =ViewHolder(
        ItemAddingFoodRcBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun getItemCount() =list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position]!!)
    }
}