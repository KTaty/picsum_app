package com.example.picsum

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.picsum.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController= navHostFragment.navController

        binding.bottomMenu.setOnItemSelectedListener{ menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item_random -> {
                    navController?.navigate(R.id.randomFragment)
                    true
                }
                else -> {
                    navController?.navigate(R.id.favoriteFragment)
                    true
                }
            }
        }
    }

}