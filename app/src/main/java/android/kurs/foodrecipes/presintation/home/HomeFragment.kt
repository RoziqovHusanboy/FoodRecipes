package android.kurs.foodrecipes.presintation.home

import android.kurs.foodrecipes.R
import android.kurs.foodrecipes.data.model.category.CategoryX
import android.kurs.foodrecipes.databinding.FragmentHomeBinding
import android.kurs.foodrecipes.presintation.add_food.AddFoodFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
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
        binding.banners.offscreenPageLimit = 1
        binding.indecator.apply {

            val normalColor = ContextCompat.getColor(requireContext(), R.color.indicator_uncheked)
            val checkedColor = ContextCompat.getColor(requireContext(), R.color.indicator_cheked)
            setSliderColor(normalColor, checkedColor)
            setSliderWidth(resources.getDimension(R.dimen.dp_20))
            setSliderHeight(resources.getDimension(R.dimen.dp_4))
            setSlideMode(IndicatorSlideMode.WORM)
            setIndicatorStyle(IndicatorStyle.ROUND_RECT)
            notifyDataChanged()
        }

        binding.floatingBar.setOnClickListener {
            val myDialog = AddFoodFragment()
            fragmentManager?.let { it1 -> myDialog.show(it1,"AddFoodFragment") }
        }
        subscribeToLiveData()
    }


    private fun subscribeToLiveData() {
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
        viewModel._home.observe(viewLifecycleOwner){
            binding.banners.adapter = BannerAdapter(it)
            binding.indecator.setupWithViewPager(binding.banners)
            binding.indecator.apply {
                setPageSize(it.banners.size)
                notifyDataChanged()
            }
        }

    }

    private fun onClick(categoryX: CategoryX){
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(categoryX.idCategory.toInt()))
    }





}