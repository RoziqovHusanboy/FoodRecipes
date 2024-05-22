package android.kurs.foodrecipes.presintation.search

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import tj.tajsoft.domain.model.network.filterByArea.Meal
import android.kurs.foodrecipes.databinding.ItemSearchRcBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener

class SearchAdapter(var list: MutableList<Meal>) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    private  var filterList: MutableList<Meal> = list

    inner class SearchViewHolder(val binding: ItemSearchRcBinding) : ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            Glide.with(binding.root).load(meal.strMealThumb).listener(object :RequestListener<Drawable>{
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
            binding.textViewTitle.text = meal.strMeal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchViewHolder(
        ItemSearchRcBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = filterList.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(filterList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        filterList = if (query.isEmpty()) {
            list
        }  else {
            list.filter { it.strMeal.toLowerCase().contains(query.toLowerCase()) }.toMutableList()
        }
        notifyDataSetChanged()
    }
}