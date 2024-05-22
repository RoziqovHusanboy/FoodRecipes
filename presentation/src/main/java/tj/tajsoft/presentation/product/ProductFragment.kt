package android.kurs.foodrecipes.presintation.product

import android.annotation.SuppressLint
import android.kurs.foodrecipes.databinding.FragmentProductBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment : Fragment() {
    private lateinit var binding: FragmentProductBinding
    private val viewModel: ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation", "NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString("title") ?: ""
        binding.title.text = title

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.loading.root.isVisible = it
            binding.product.isVisible = !it
        }

        viewModel._product.observe(viewLifecycleOwner) {
            val filteredList = it.filter { it.name ==binding.title.text }.toMutableList()
            binding.recyclerViewProduct.adapter = ProductAdapter(filteredList, ::onClick)
        }
    }

    private fun onClick(id: String) {
        findNavController().navigate(
            ProductFragmentDirections.actionProductFragmentToDetailProductFragment(id)
        )
    }

}