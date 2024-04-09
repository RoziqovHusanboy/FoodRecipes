package android.kurs.foodrecipes.presintation.home

import android.kurs.foodrecipes.data.model.category.CategoryX
import android.kurs.foodrecipes.databinding.FragmentHomeBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.search.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
        }
        SubscribeToLiveData()
    }


    private fun SubscribeToLiveData() {
        viewModel.error.observe(viewLifecycleOwner){
            binding.error.root.isVisible = it
            binding.error.errorBtn.setOnClickListener {
                viewModel.getCategory()
            }
        }

        viewModel.loading.observe(viewLifecycleOwner){
            binding.loading.root.isVisible = it
        }

        viewModel._category.observe(viewLifecycleOwner){category->
            binding.recyclerPopular.layoutManager =LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            binding.recyclerPopular.adapter =   PopularItemAdapter(category.categories,this::onClick)
        }
    }

    private fun onClick(categoryX: CategoryX){
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(categoryX.idCategory.toInt()))
    }



}