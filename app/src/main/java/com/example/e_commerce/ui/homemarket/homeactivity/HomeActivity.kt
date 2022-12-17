package com.example.e_commerce.ui.homemarket.homeactivity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.e_commerce.Constants.ANONYMOUS
import com.example.e_commerce.Constants.NAV_ACTION_LOGIN
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
import com.example.e_commerce.utils.ToastyUtil
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() , NavigationBarView.OnItemSelectedListener {

    private lateinit var binding : ActivityHomeBinding
    private val viewModel: HomeActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home) //Initialize binding
        binding.lifecycleOwner = this //Set lifecycleOwner
        binding.viewModel = viewModel //Set Variable Of ViewModel (DataBinding)
        setOnClickOnLogoutIcon()      //Set On Click On Logout Icon
        setOnClickOnProfileCard()     //Set On Click On Profile Card
        observeSignOut()              //Observe SignOut
        observeErrorMessage()         //Observe Error
        binding.bottomNavigationView.setOnItemSelectedListener(this) //set Item Selected Listener Of bottomNavigationView
        supportFragmentManager.beginTransaction().replace(R.id.flFragment, HomeFragment()).commit() //Replace HomeFragment

    }

    private fun observeErrorMessage() {
        viewModel.error.observe(this){
            it?.let {
                ToastyUtil.errorToasty(this,it,Toast.LENGTH_SHORT) //Toast Error Message
            }
        }
    }

    private fun setOnClickOnProfileCard() {
        binding.profileCardHomeActivity.setOnClickListener {
            if (SharedPrefsUtil.getType(this) != ANONYMOUS) {
                binding.bottomNavigationView.selectedItemId = R.id.account   //Set Selected Item Of bottomNavigationView
                binding.title = getString(R.string.account)                                    //Set Text Of Title
                supportFragmentManager.beginTransaction().replace(R.id.flFragment, AccountFragment()).commit() //Replace AccountFragment
            }
        }
    }

    private fun setOnClickOnLogoutIcon() {
        binding.logoutHomeActivity.setOnClickListener {
            showDialog(getString(R.string.Are_you_sure_you_want_to_Logout)) //Show Dialog
        }
    }

    private fun showDialog(message : String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
            .setCancelable(true)
            .setPositiveButton(getString(R.string.Yes)) { dialog, _ ->
                viewModel.signOut()
                dialog.dismiss()    //Dismiss the dialog
            }
            .setNegativeButton(getString(R.string.No)) { dialog, _ ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun observeSignOut(){
        viewModel.liveDataSignedOut.observe(this){
            if (it){
                val intent = Intent(this, MainActivity::class.java) //Intent To MainActivity
                intent.putExtra(NAV_ACTION_LOGIN,R.id.action_splashFragment_to_signInFragment)
                startActivity(intent)
                finish()
            }
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        viewModel.getProfileImage()
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
                if (SharedPrefsUtil.getType(this) != ANONYMOUS) {
                    binding.title = getString(R.string.account)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flFragment, AccountFragment())
                        .commit()
                }else{showDialog(getString(R.string.This_section_requires_that_you_be_signed_in_Do_You_Want_To_Sign_In_Now))}
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