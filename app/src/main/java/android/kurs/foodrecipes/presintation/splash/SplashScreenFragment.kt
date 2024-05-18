package android.kurs.foodrecipes.presintation.splash

import android.kurs.foodrecipes.R
import android.kurs.foodrecipes.databinding.FragmentSplashScreenBinding
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenFragment:Fragment() {
    private lateinit var binding:FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val animationSlideIN = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in)
        val animationSlideOut = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out)

        android.os.Handler(Looper.getMainLooper()).postDelayed({
            binding.imageLogo.startAnimation(animationSlideIN)
            binding.imageLogo.startAnimation(animationSlideOut)
              findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToLogInFragment())
        },2000)
    }
}