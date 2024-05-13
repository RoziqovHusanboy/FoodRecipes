package android.kurs.foodrecipes.presintation.home

import android.kurs.foodrecipes.databinding.FragmentDetailBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()
    private val viewmodel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            this.back.setOnClickListener {
                findNavController().popBackStack()
            }
            viewmodel._category.observe(viewLifecycleOwner) {
                it.categories.forEach {
                    if (args.id == it.idCategory.toInt()) {
                        this.title.text = it.strCategory
                        this.destination.text = it.strCategoryDescription
                        Glide.with(this.root).load(it.strCategoryThumb).into(this.image)
                    }
                }
            }

            viewmodel.error.observe(viewLifecycleOwner){
                binding.progress.isVisible = it
            }
        }
    }

}