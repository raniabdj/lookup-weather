package com.example.lookupweather.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import android.view.View

import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView







class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.lookupweather.R.layout.activity_main)
       // setSupportActionBar(tool)


        setUpNavigation()

    }
    fun setUpNavigation() {
        bottomNavigationView = findViewById(com.example.lookupweather.R.id.bottomNav)
        val navHostFragment = supportFragmentManager
            .findFragmentById(com.example.lookupweather.R.id.fr) as NavHostFragment?
        NavigationUI.setupWithNavController(
            bottomNavigationView!!,
            navHostFragment!!.navController,
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }
}