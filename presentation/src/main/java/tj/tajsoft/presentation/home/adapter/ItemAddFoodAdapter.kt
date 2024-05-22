package android.kurs.foodrecipes.presintation.home.adapter

import tj.tajsoft.data.local.model.FoodAddModel
import android.kurs.foodrecipes.databinding.ItemAddingFoodRcBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ItemAddFoodAdapter(
    val list :MutableList<FoodAddModel?>,
    val swipeListener: OnItemSwipeListener
):RecyclerView.Adapter<ItemAddFoodAdapter.ViewHolder>() {
    interface OnItemSwipeListener {
        fun onItemSwiped(position: Int)
    }
    inner class ViewHolder(val binding:ItemAddingFoodRcBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: FoodAddModel){
            Glide.with(binding.root).load(item.url).into(binding.image)
//            binding.image.setImageURI(item.url)
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
    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

}