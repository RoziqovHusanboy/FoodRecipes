package android.kurs.foodrecipes.presentation.cart

import android.annotation.SuppressLint
import tj.tajsoft.domain.model.local.Cart
import android.kurs.foodrecipes.databinding.FragmentCartBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    val viewModel by viewModels<CartViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.delete.setOnClickListener {
            viewModel.clear()
        }

        viewModel.carts.observe(viewLifecycleOwner) {
            binding.products.adapter?.notifyDataSetChanged()
            it?.let {

                binding.products.adapter =
                    CartAdapter(
                        it,
                        this@CartFragment::set
                    )
                binding.empty.isVisible = it.isEmpty()
                binding.content.isVisible = it.isNotEmpty()
                if (it.isNotEmpty()) {
                    var totalPrice = 0
                    var totalCount = 0
                    it.forEach { cartItem ->
                        val price = cartItem.price.toInt()
                        val count = cartItem.count
                        totalPrice += price * count
                        totalCount += count
                    }
                    binding.itemTotal.text = "$$totalPrice"
                    binding.count.text = "$totalCount"
                    binding.total.text = "$$totalPrice"
                }
            }
        }


    }

    private fun set(cart: Cart) {
        viewModel.set(cart)
    }
}