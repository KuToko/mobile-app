package com.example.kutoko

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kutoko.data.MyStore
import com.example.kutoko.databinding.ActivityMainBinding
import com.example.kutoko.ui.beranda.BerandaFragment
import com.example.kutoko.ui.tokosaya.beranda.BerandaTokoSaya
import com.example.kutoko.ui.tokosaya.beranda.BerandaTokoSayaViewModel
import com.example.kutoko.ui.userLocation.FetchUserLocation

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private lateinit var berandaTokoSayaViewModel: BerandaTokoSayaViewModel

    companion object {
        const val CHANGE_NAV = "change_nav"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        berandaTokoSayaViewModel = ViewModelProvider(this)[BerandaTokoSayaViewModel::class.java]
        val navView: BottomNavigationView = binding.navView

        val check = intent.getBooleanExtra(CHANGE_NAV,false)


//        binding.navView.menu
        val navController = findNavController(R.id.nav_host_fragment_activity_main)



        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_beranda, R.id.navigation_cari, R.id.navigation_favorite, R.id.navigation_profile
            )
        )

        val myStoreConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_order
            )
        )

        if (check){
            navView.menu.clear()
            navView.inflateMenu(R.menu.bottom_nav_mystore)
            navController.setGraph(R.navigation.tokosaya_navigation)
            setupActionBarWithNavController(navController, myStoreConfiguration)
            navView.setupWithNavController(navController)

        }else{
            navView.menu.clear()
            navView.inflateMenu(R.menu.bottom_nav_menu)
            navController.setGraph(R.navigation.mobile_navigation)
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }



        @Suppress("DEPRECATION")
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
                if (currentFragment is BerandaFragment) {
                    finishAffinity()
                } else {
                    isEnabled = false
                    onBackPressed()
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }
}