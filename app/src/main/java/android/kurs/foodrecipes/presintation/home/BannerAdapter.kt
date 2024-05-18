package android.kurs.foodrecipes.presintation.home

import android.kurs.foodrecipes.data.model.home.Banner
import android.kurs.foodrecipes.data.model.home.ResponseHome
import android.kurs.foodrecipes.databinding.ItemBannerBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BannerAdapter(
    private val banners:ResponseHome
) : RecyclerView.Adapter<BannerAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(banner: Banner) = with(binding) {
            Glide.with(root).load(banner.image).into(image)
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