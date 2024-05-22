package android.kurs.foodrecipes.presentation.home.adapter

import android.graphics.drawable.Drawable
import tj.tajsoft.domain.model.network.category.CategoryX
import android.kurs.foodrecipes.databinding.PopularRecyclerItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class PopularItemAdapter(val list:List<CategoryX>, val onClick:(categoryX: CategoryX)->Unit):RecyclerView.Adapter<PopularItemAdapter.VH>() {
    inner class VH(val binding:PopularRecyclerItemBinding):ViewHolder(binding.root){

        fun bind(categoryX: CategoryX){
            Glide.with(binding.root).load(categoryX.strCategoryThumb).listener(object :
                RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.loading.isVisible = false
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.loading.isVisible = false
                    return false
                }


            }).into(binding.imageview)

            binding.title.text = categoryX.strCategory
            binding.root.setOnClickListener {
                onClick(categoryX)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        PopularRecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun getItemCount() =list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(list[position])
    }
}