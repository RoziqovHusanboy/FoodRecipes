package android.kurs.foodrecipes.presentation.onboarding

import android.kurs.foodrecipes.R
import android.kurs.foodrecipes.databinding.FragmentOnboardingPageBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class OnboardingAdapter : RecyclerView.Adapter<OnboardingAdapter.ViewHolder>() {

    class ViewHolder(private val binding: FragmentOnboardingPageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(page: Triple<Int, Int, Int>) {
            Glide.with(binding.root.context).load(page.first).into(binding.image)
            binding.title.text = binding.root.context.getString(page.second)
            binding.description.text = binding.root.context.getString(page.third)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        FragmentOnboardingPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    companion object{
        private val items = listOf(
            Triple(
                R.drawable.onboarind_image_0,
                R.string.onboarding_title_0,
                R.string.onboarding_desc_0
            ),
            Triple(
                R.drawable.image_onboarding1,
                R.string.onboarding_title_1,
                R.string.onboarding_desc_1
            )
        )
    }
}
