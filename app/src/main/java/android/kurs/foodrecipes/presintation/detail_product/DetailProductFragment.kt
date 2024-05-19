package android.kurs.foodrecipes.presintation.detail_product

import android.graphics.drawable.Drawable
import android.kurs.foodrecipes.R
import android.kurs.foodrecipes.databinding.FragmentProductDetailBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailProductFragment: Fragment() {
private lateinit var binding:FragmentProductDetailBinding
private val viewModel:DetailProductViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var id=""
        arguments?.let {
            id = it.getString("id")!!
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.minus.setOnClickListener {
            viewModel.decrement()
        }
        binding.plus.setOnClickListener {
            viewModel.increment()
        }

        viewModel._product.observe(viewLifecycleOwner){
            it.forEach {
                if (it.id==id){
                    Glide.with(binding.root).load(it.image).listener(object :RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding.progress.isVisible = false
                            return false
                        }
                        override fun onResourceReady(
                            resource: Drawable,
                            model: Any,
                            target: Target<Drawable>?,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding.progress.isVisible = false
                            return false
                        }
                    }).into(binding.image)

                    binding.title.text = it.title
                    binding.price.text = "$${it.price}"
                    binding.description.text = it.desc
                    binding.products.adapter =RelatedAdapter(it.related)
                    viewModel.count.observe(viewLifecycleOwner){
                        binding.count.text = it.toString()
                    }

                    binding.add.setOnClickListener {view->
                        viewModel.set(
                            id = it.id,
                            title = it.title,
                            image = it.image,
                            price = it.price.toString().toDouble()
                        )
                        Snackbar.make(binding.root, R.string.fragment_detail_cart_added,Snackbar.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                }
            }
        }


    }
}