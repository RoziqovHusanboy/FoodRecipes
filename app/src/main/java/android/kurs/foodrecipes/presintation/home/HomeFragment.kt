package android.kurs.foodrecipes.presintation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.kurs.foodrecipes.R
import android.kurs.foodrecipes.databinding.FragmentHomeBinding
import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SubscribeToLiveData()
        UI()

    }

    private fun UI() {

    }
    private fun SubscribeToLiveData() {

//        viewModel.error.observe(viewLifecycleOwner){
//            binding.error.isVisible = it
//        }

        viewModel._error.observe(viewLifecycleOwner){
            binding.error.text = it.toString()
        }



        viewModel._category.observe(viewLifecycleOwner){
            Log.d("TAG", "SubscribeToLiveData:$it ")
            it.categories.forEach {
                binding.log.text =   it.idCategory
            }
        }

    }

}