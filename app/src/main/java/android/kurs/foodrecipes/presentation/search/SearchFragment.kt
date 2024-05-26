package android.kurs.foodrecipes.presentation.search

import android.kurs.foodrecipes.R
import android.kurs.foodrecipes.databinding.FragmentSearchBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchView.OnQueryTextListener {
    lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var recyclerViewAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getText()
        SubscribeToLiveData()
        val searchView = view.findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(this)
    }

    private fun SubscribeToLiveData() {

        viewModel.filterFoods.observe(viewLifecycleOwner) {
           val mutableList =  it.toMutableList()
            recyclerViewAdapter = SearchAdapter(mutableList)
            binding.recyclerViewFoods.adapter = recyclerViewAdapter
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.loading.progress.isVisible = it
        }


    }

    private fun getText() {
        binding.apply {
            textViewAmerica.setOnClickListener {
                viewModel.getFilteringFoods("American")
            }
            textViewIndian.setOnClickListener {
                viewModel.getFilteringFoods("Indian")
            }
            textViewCanadian.setOnClickListener {
                viewModel.getFilteringFoods("Canadian")
            }
            textViewItalian.setOnClickListener {
                viewModel.getFilteringFoods("Italian")
            }
            textViewRussian.setOnClickListener {
                viewModel.getFilteringFoods("Russian")
            }
            textViewSpanish.setOnClickListener {
                viewModel.getFilteringFoods("Spanish")
            }
            textViewTurkish.setOnClickListener {
                viewModel.getFilteringFoods("Turkish")
            }

        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        recyclerViewAdapter.filter(query!!)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        recyclerViewAdapter.filter(query = newText!!)
        return false
    }
}