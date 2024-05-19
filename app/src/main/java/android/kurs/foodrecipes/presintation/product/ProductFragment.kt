package android.kurs.foodrecipes.presintation.product

import android.annotation.SuppressLint
import android.kurs.foodrecipes.data.model.home.get_category.ResponseProductsItem
import android.kurs.foodrecipes.databinding.FragmentProductBinding
import android.os.Bundle
import android.util.Log
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
        var title = ""
        val listProduct: MutableList<ResponseProductsItem> = mutableListOf()

        arguments?.let {
            title = it.getString("title")!!
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            binding.loading.root.isVisible = it
            binding.product.isVisible = !it
        }

        viewModel._product.observe(viewLifecycleOwner)
        {
            binding.title.text = title
            it.forEach {
                if (title == it.name){
                    listProduct.add(it)
                    Log.d("TAG", "onViewCreated:listProduct.add(it) $it ")
                }
            }
            binding.recyclerViewProduct.adapter?.notifyDataSetChanged()
        }

        binding.recyclerViewProduct.adapter = ProductAdapter(listProduct,::onClick)
    }

    private fun onClick(id:String){
        findNavController().navigate(ProductFragmentDirections.actionProductFragmentToDetailProductFragment(id))
    }

}