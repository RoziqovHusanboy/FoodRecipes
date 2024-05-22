package android.kurs.foodrecipes.presintation.home

import android.annotation.SuppressLint
import android.kurs.foodrecipes.R
import tj.tajsoft.domain.model.network.category.CategoryX
import android.kurs.foodrecipes.databinding.FragmentHomeBinding
import android.kurs.foodrecipes.presintation.home.adapter.BannerAdapter
import android.kurs.foodrecipes.presintation.home.adapter.ItemAddFoodAdapter
import android.kurs.foodrecipes.presintation.home.adapter.PopularItemAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(),View.OnClickListener , ItemAddFoodAdapter.OnItemSwipeListener {
    private lateinit var binding:FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var mAuth: FirebaseAuth
    private lateinit var adapter: ItemAddFoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        viewModel.getNewFood(requireContext())
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
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddFoodFragment())
        }

        binding.saladConstraint.setOnClickListener(this)
        binding.drinksConstraint.setOnClickListener(this)
        binding.mainConstraint.setOnClickListener(this)
        binding.dessertConstraint.setOnClickListener(this)

        subscribeToLiveData()
    }


    @SuppressLint("NotifyDataSetChanged")
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

        viewModel.getNewFood.observe(viewLifecycleOwner){
            it?.let {
                binding.recyclerLocalFood.adapter?.notifyDataSetChanged()
                 adapter = ItemAddFoodAdapter(it,this)
                binding.recyclerLocalFood.adapter =adapter
                val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter))
                itemTouchHelper.attachToRecyclerView(binding.recyclerLocalFood)
            }
        }


    }

    private fun putStringToProductFragment(title:String){
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductFragment(title))
    }


    private fun onClick(categoryX: CategoryX){
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(categoryX.idCategory.toInt()))
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.saladConstraint ->{
                putStringToProductFragment("salad")
            }
            R.id.mainConstraint ->{
                putStringToProductFragment("main")
            }
            R.id.drinksConstraint ->{
                putStringToProductFragment("drink")
            }
            R.id.dessertConstraint ->{
                putStringToProductFragment("dessert")
            }

        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNewFood(requireContext())
    }

    override fun onItemSwiped(position: Int) {
        adapter.removeItem(position)
        viewModel.removeByPositionFromFoodStore(requireContext(), position)
    }


}