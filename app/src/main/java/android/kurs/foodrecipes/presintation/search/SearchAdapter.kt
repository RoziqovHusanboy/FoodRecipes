package android.kurs.foodrecipes.presintation.search

import android.kurs.foodrecipes.data.model.filterByArea.Meal
import android.kurs.foodrecipes.data.model.filterByArea.ResponseFilterByArea
import android.kurs.foodrecipes.databinding.ItemSearchRcBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide

class SearchAdapter(var list: ArrayList<Meal>) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(), Filterable {
    private  var filterList: ArrayList<Meal> = list

    inner class SearchViewHolder(val binding: ItemSearchRcBinding) : ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            Glide.with(binding.root).load(meal.strMealThumb).into(binding.image)
            binding.textViewTitle.text = meal.strMeal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchViewHolder(
        ItemSearchRcBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = filterList.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                if (constraint.toString().isEmpty())
                    filterList = list
                else {
                    val filteredList = ArrayList<Meal>()
                    list.forEach {
                        if (it.strMeal.toLowerCase().contains(constraint.toString())) {
                            filteredList.add(it)
                        }
                        filterList = filteredList
                    }
                }
                val result =FilterResults()
                result.values = filterList
                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = results?.values as ArrayList<Meal>
                notifyDataSetChanged()
            }
        }
    }
}