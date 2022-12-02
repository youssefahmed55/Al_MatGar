package com.example.e_commerce.ui.homemarket.home

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.e_commerce.BuildConfig
import com.example.e_commerce.R
import com.example.e_commerce.adapters.CategoriesRecyclerAdapter
import com.example.e_commerce.adapters.ProductsHomeRecyclerAdapter
import com.example.e_commerce.databinding.FragmentHomeBinding
import com.example.e_commerce.pojo.Category
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.ui.login.MainActivity
import com.example.e_commerce.ui.homemarket.subcategory.productdetails.ProductDetailsFragment
import com.example.e_commerce.ui.homemarket.subcategory.subcategory.SubCategoryFragment
import com.example.e_commerce.utils.SharedPrefsUtil
import com.example.e_commerce.utils.SignedInUtil
import com.example.e_commerce.utils.ToastyUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "HomeFragment"
@AndroidEntryPoint
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private lateinit var binding:FragmentHomeBinding
    private val categoriesRecyclerAdapter : CategoriesRecyclerAdapter by lazy { CategoriesRecyclerAdapter() }
    private val beautyRecyclerAdapter : ProductsHomeRecyclerAdapter by lazy { ProductsHomeRecyclerAdapter() }
    private val foodRecyclerAdapter : ProductsHomeRecyclerAdapter by lazy { ProductsHomeRecyclerAdapter() }
    private val clothesRecyclerAdapter : ProductsHomeRecyclerAdapter by lazy { ProductsHomeRecyclerAdapter() }
    private val houseWareRecyclerAdapter : ProductsHomeRecyclerAdapter by lazy { ProductsHomeRecyclerAdapter() }
    private val viewModel: HomeFragmentViewModel by lazy { ViewModelProvider(this)[HomeFragmentViewModel::class.java] }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        setOnClickOnCategoriesItem()
        setOnClickOnBeautyItem()
        setOnClickOnFoodItem()
        setOnClickOnClothesItem()
        setOnClickOnHouseWareItem()
        binding.viewModel = viewModel
        binding.categoriesRecyclerAdapter = categoriesRecyclerAdapter
        binding.beautyRecyclerAdapter = beautyRecyclerAdapter
        binding.foodRecyclerAdapter = foodRecyclerAdapter
        binding.clothesRecyclerAdapter = clothesRecyclerAdapter
        binding.houseWareRecyclerAdapter = houseWareRecyclerAdapter

        observeErrorMessage()
        onClickOnLogoutIcon()

        return binding.root
    }

    private fun replaceProductDetailsFragment(product : Product){
        val args = Bundle()
        args.putSerializable("product", product)
        val productDetailsFragment = ProductDetailsFragment()
        productDetailsFragment.arguments = args
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, productDetailsFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun observeErrorMessage() {
        viewModel.error.observe(viewLifecycleOwner, Observer {
            it?.let {
                ToastyUtil.errorToasty(context!!,it,Toast.LENGTH_SHORT)
                viewModel.getRoomDataBase()
                viewModel._error.value = null
            }
        })
    }

    private fun setOnClickOnFoodItem() {
        foodRecyclerAdapter.setOnItemClickListener(object : ProductsHomeRecyclerAdapter.OnClickOnItem{
            override fun onClick1(product: Product) {
                replaceProductDetailsFragment(product)
            }
        })
    }

    private fun setOnClickOnBeautyItem() {
        beautyRecyclerAdapter.setOnItemClickListener(object : ProductsHomeRecyclerAdapter.OnClickOnItem{
            override fun onClick1(product: Product) {
                replaceProductDetailsFragment(product)
            }
        })
    }

    private fun setOnClickOnCategoriesItem() {
        categoriesRecyclerAdapter.setOnItemClickListener(object : CategoriesRecyclerAdapter.OnClickOnItem{
            override fun onClick1(cat: Category) {
                val args = Bundle()
                args.putInt("catId", cat.id)
                args.putString("catName", cat.name)
                val subCategoryFragment = SubCategoryFragment()
                subCategoryFragment.arguments = args
                val transaction = activity!!.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.flFragment, subCategoryFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })
    }

    private fun setOnClickOnHouseWareItem() {
        houseWareRecyclerAdapter.setOnItemClickListener(object : ProductsHomeRecyclerAdapter.OnClickOnItem{
            override fun onClick1(product: Product) {
                replaceProductDetailsFragment(product)
            }
        })
    }

    private fun setOnClickOnClothesItem() {
        clothesRecyclerAdapter.setOnItemClickListener(object : ProductsHomeRecyclerAdapter.OnClickOnItem{
            override fun onClick1(product: Product) {
                replaceProductDetailsFragment(product)
            }
        })
    }

    private fun onClickOnLogoutIcon() {
        binding.logoutHomeFragment.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(context!!)
        builder.setMessage(getString(R.string.Are_you_sure_you_want_to_Logout))
            .setCancelable(true)
            .setPositiveButton(getString(R.string.Yes)) { dialog, _ ->

                FirebaseAuth.getInstance().signOut()
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(BuildConfig.GOOGLE_API_KEY)
                    .requestEmail()
                    .build()
                val googleClient = GoogleSignIn.getClient(context!!, gso)
                googleClient.signOut()
                SharedPrefsUtil.clearUserModel(context!!)
                SignedInUtil.setIsSignIn(context!!,false)
                activity?.startActivity(Intent(activity, MainActivity::class.java))
                activity?.finish()

                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.No)) { dialog, _ ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}