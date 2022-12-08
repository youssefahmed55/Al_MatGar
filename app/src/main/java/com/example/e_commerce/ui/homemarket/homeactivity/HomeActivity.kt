package com.example.e_commerce.ui.homemarket.homeactivity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.e_commerce.BuildConfig
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ActivityHomeBinding
import com.example.e_commerce.ui.homemarket.account.AccountFragment
import com.example.e_commerce.ui.homemarket.cart.cart.CartFragment
import com.example.e_commerce.ui.homemarket.explore.ExploreFragment
import com.example.e_commerce.ui.homemarket.home.HomeFragment
import com.example.e_commerce.ui.homemarket.myproducts.myproducts.MyProductsFragment
import com.example.e_commerce.ui.homemarket.offer.OfferFragment
import com.example.e_commerce.ui.homemarket.users.users.UsersFragment
import com.example.e_commerce.ui.login.MainActivity
import com.example.e_commerce.utils.SharedPrefsUtil
import com.example.e_commerce.utils.SignedInUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() , NavigationBarView.OnItemSelectedListener {

    private lateinit var binding : ActivityHomeBinding
    private val viewModel: HomeActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setOnClickOnLogoutIcon()

        binding.bottomNavigationView.setOnItemSelectedListener(this)
        supportFragmentManager.beginTransaction().replace(R.id.flFragment, HomeFragment()).commit()

    }

    private fun setOnClickOnLogoutIcon() {
        binding.logoutHomeActivity.setOnClickListener {
            showDialogLogOut()
        }
    }

    private fun showDialogLogOut() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.Are_you_sure_you_want_to_Logout))
            .setCancelable(true)
            .setPositiveButton(getString(R.string.Yes)) { dialog, _ ->

                FirebaseAuth.getInstance().signOut()
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(BuildConfig.GOOGLE_API_KEY)
                    .requestEmail()
                    .build()
                val googleClient = GoogleSignIn.getClient(this, gso)
                googleClient.signOut()
                SharedPrefsUtil.clearUserModel(this)
                SignedInUtil.setIsSignIn(this,false)
                startActivity(Intent(this, MainActivity::class.java))
                finish()

                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.No)) { dialog, _ ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                binding.title = ""
                supportFragmentManager.beginTransaction().replace(R.id.flFragment, HomeFragment())
                    .commit()
                return true
            }
            R.id.explore -> {
                binding.title  = getString(R.string.explore)
                supportFragmentManager.beginTransaction().replace(R.id.flFragment, ExploreFragment())
                    .commit()
                return true
            }
            R.id.cart -> {
                binding.title  = getString(R.string.cart)
                supportFragmentManager.beginTransaction().replace(R.id.flFragment, CartFragment())
                    .commit()
                return true
            }
            R.id.offer -> {
                binding.title  = getString(R.string.offer)
                supportFragmentManager.beginTransaction().replace(R.id.flFragment, OfferFragment())
                    .commit()
                return true
            }
            R.id.account -> {
                binding.title  = getString(R.string.account)
                supportFragmentManager.beginTransaction().replace(R.id.flFragment, AccountFragment())
                    .commit()
                return true
            }
            R.id.addUsers -> {
                binding.title  = getString(R.string.users)
                supportFragmentManager.beginTransaction().replace(R.id.flFragment, UsersFragment())
                    .commit()
                return true
            }
            R.id.myProducts -> {
                binding.title  = getString(R.string.my_products)
                supportFragmentManager.beginTransaction().replace(R.id.flFragment, MyProductsFragment())
                    .commit()
                return true
            }
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}