package android.kurs.foodrecipes.presentation.main

import android.kurs.foodrecipes.R
import android.kurs.foodrecipes.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.forEachIndexed
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val navController get() = (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getActionBar()?.hide();

        binding.apply {
            bottomNavigation.setupWithNavController(navController)
            bottomNavigation.setOnItemSelectedListener {
                var index: Int = 0
                bottomNavigation.menu.forEachIndexed { itemIndex, item ->
                    if (it.itemId != item.itemId) return@forEachIndexed
                    index = itemIndex
                }
                NavigationUI.onNavDestinationSelected(it, navController)
                return@setOnItemSelectedListener true
            }
            navController.addOnDestinationChangedListener { _, destination, _ ->
                bottomNavigation.isVisible =
                    listOf(
                        R.id.SignInFragment,
                        R.id.signUpFragment,
                        R.id.forgotFragment,
                        R.id.detailFragment,
                        R.id.searchFragment,
                        R.id.profileFragment,
                        R.id.splashScreenFragment,
                        R.id.productFragment,
                        R.id.detailProductFragment,
                        R.id.onboardingFragment
                    ).all {
                        it != destination.id
                    }
            }
        }
    }
}


