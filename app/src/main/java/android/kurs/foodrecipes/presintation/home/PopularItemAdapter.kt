package android.kurs.foodrecipes.presintation.home

import android.kurs.foodrecipes.data.model.category.CategoryX
import android.kurs.foodrecipes.databinding.PopularRecyclerItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide

class PopularItemAdapter(val list:List<CategoryX>, val onClick:(categoryX: CategoryX)->Unit):RecyclerView.Adapter<PopularItemAdapter.VH>() {
    inner class VH(val binding:PopularRecyclerItemBinding):ViewHolder(binding.root){

        fun bind(categoryX: CategoryX){
            Glide.with(binding.root).load(categoryX.strCategoryThumb).into(binding.imageview)
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