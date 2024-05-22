package android.kurs.foodrecipes.presintation.home.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import tj.tajsoft.domain.model.network.home.get_home.Banner
import tj.tajsoft.domain.model.network.home.get_home.ResponseHome
import android.kurs.foodrecipes.databinding.ItemBannerBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener

class BannerAdapter(
    private val banners: ResponseHome
) : RecyclerView.Adapter<BannerAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(banner: Banner) = with(binding) {
            Glide.with(root).load(banner.image).listener(object :RequestListener<Drawable>{
                @SuppressLint("SuspiciousIndentation")
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                loading.isVisible = false
                    return false
                }
                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    loading.isVisible = false
                    return false
                }

            }).into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = banners.banners.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(banners.banners[position])
    }

}