package com.example.e_commerce.ui.homemarket

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerce.R
import com.example.e_commerce.ui.homemarket.account.AccountFragment
import com.example.e_commerce.ui.homemarket.cart.CartFragment
import com.example.e_commerce.ui.homemarket.explore.ExploreFragment
import com.example.e_commerce.ui.homemarket.home.HomeFragment
import com.example.e_commerce.ui.homemarket.offer.OfferFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class HomeActivity : AppCompatActivity() , NavigationBarView.OnItemSelectedListener {

    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener(this)
        bottomNavigationView.selectedItemId = R.id.home
        supportFragmentManager.beginTransaction().replace(R.id.flFragment, HomeFragment()).commit()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                supportFragmentManager.beginTransaction().replace(R.id.flFragment, HomeFragment())
                    .commit()
                return true
            }
            R.id.explore -> {
                supportFragmentManager.beginTransaction().replace(R.id.flFragment, ExploreFragment())
                    .commit()
                return true
            }
            R.id.cart -> {
                supportFragmentManager.beginTransaction().replace(R.id.flFragment, CartFragment())
                    .commit()
                return true
            }
            R.id.offer -> {
                supportFragmentManager.beginTransaction().replace(R.id.flFragment, OfferFragment())
                    .commit()
                return true
            }
            R.id.account -> {
                supportFragmentManager.beginTransaction().replace(R.id.flFragment, AccountFragment())
                    .commit()
                return true
            }
        }
        return false
    }
}