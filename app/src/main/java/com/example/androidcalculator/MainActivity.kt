package com.example.androidcalculator

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.androidcalculator.databinding.ActivityMainBinding
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val operations = mutableListOf<OperationUi>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        permissionsBuilder(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION).build().send{
                result ->
            if (result.allGranted())
            {
                if (!screenRotated(savedInstanceState)) {
                    NavigationManager.goToCalculatorFragment(supportFragmentManager)
                }
            } else {
                finish()
            }
        }
    }

    private fun screenRotated(savedInstanceState: Bundle?): Boolean {
        return savedInstanceState != null
    }

    override fun onStart() {
        super.onStart()
        setSupportActionBar(binding.toolbar)
        setupDrawerMenu()
    }

    private fun setupDrawerMenu() {
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawer, binding.toolbar,
            R.string.drawer_open, R.string.drawer_close
        )
        binding.navDrawer.setNavigationItemSelectedListener {
            onClickNavigationItem(it)
        }
        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun onClickNavigationItem(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_calculator -> NavigationManager.goToCalculatorFragment(supportFragmentManager)
            R.id.nav_history -> NavigationManager.goToHistoryFragment(supportFragmentManager)
            R.id.nav_map -> NavigationManager.goToMapFragment(supportFragmentManager)
        }
        binding.drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
            binding.drawer.closeDrawer(GravityCompat.START)
        } else if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    fun addOperation(operation: OperationUi) {
        operations.add(operation)
    }

    fun getOperations() = operations


}