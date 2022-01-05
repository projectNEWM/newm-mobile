package com.projectnewm

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.projectnewm.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navView: BottomNavigationView = binding.bottomNav

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_community,
                R.id.navigation_stars,
                R.id.navigation_wallet
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(
                ContextCompat.getColor(this, R.color.gradient_blue),
                ContextCompat.getColor(this, R.color.gradient_dark_blue),
                ContextCompat.getColor(this, R.color.gradient_purple),
                ContextCompat.getColor(this, R.color.gradient_pink),
                ContextCompat.getColor(this, R.color.gradient_red),
                ContextCompat.getColor(this, R.color.gradient_orange),
                ContextCompat.getColor(this, R.color.gradient_yellow),
                ContextCompat.getColor(this, R.color.gradient_green),
            )
        )
        val fadeGradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(
                ContextCompat.getColor(this, R.color.gradient_blue_fade),
                ContextCompat.getColor(this, R.color.gradient_dark_blue_fade),
                ContextCompat.getColor(this, R.color.gradient_purple_fade),
                ContextCompat.getColor(this, R.color.gradient_pink_fade),
                ContextCompat.getColor(this, R.color.gradient_red_fade),
                ContextCompat.getColor(this, R.color.gradient_orange_fade),
                ContextCompat.getColor(this, R.color.gradient_yellow_fade),
                ContextCompat.getColor(this, R.color.gradient_green_fade),
            )
        )
        binding.gradient.background = gradientDrawable

        // Messing around trying to make it glow a little more :-D
        binding.gradientBottomFade.background = fadeGradientDrawable
        binding.gradientTopFade.background = fadeGradientDrawable

        navController.addOnDestinationChangedListener { controller, destination, args ->
            binding.tabs.isVisible = when (destination.id) {
                R.id.navigation_home -> true
                else -> false
            }
        }

        // Test KMM is wired:
        Log.i("HomeActivity", "Hello from shared module: " + (Greeting().greeting()))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}