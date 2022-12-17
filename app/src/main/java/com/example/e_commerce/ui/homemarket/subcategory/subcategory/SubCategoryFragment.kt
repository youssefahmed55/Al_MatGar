package com.example.e_commerce.ui.homemarket.subcategory.subcategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.e_commerce.Constants.PRODUCT
import com.example.e_commerce.R
import com.example.e_commerce.adapters.ProductsSubExploreRecyclerAdapter
import com.example.e_commerce.databinding.FragmentSubCategoryBinding
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.ui.homemarket.subcategory.productdetails.ProductDetailsFragment
import com.example.e_commerce.utils.ToastyUtil
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SubCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SubCategoryFragment : Fragment()  {
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



    private lateinit var binding : FragmentSubCategoryBinding
    private val productsRecyclerAdapter : ProductsSubExploreRecyclerAdapter by lazy { ProductsSubExploreRecyclerAdapter() }
    private val viewModel: SubCategoryViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity!!.findViewById<RelativeLayout>(R.id.relative1_homeActivity).visibility = View.GONE //Set Activity's RelativeLayout GONE
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_sub_category, container, false) //Initialize binding
        binding.lifecycleOwner = this                  //Set lifecycleOwner
        binding.viewModel = viewModel                  //Set Variable Of ViewModel (DataBinding)
        setOnClickOnRecyclerItem()                     //Set On Click On Recycler Item
        setOnClickOnFavoriteOfRecyclerItem()           //Set On Click On Favorite Recycler Item
        productsRecyclerAdapter.setContext(context!!)  //Set Context To Recycler
        binding.adapter = productsRecyclerAdapter      //Set Variable Of adapter (DataBinding)
        setOnClickOnBackIcon()                         //Set On Click On Back Icon
        observeErrorMessage()                          //Observe Error
        observeDeletedFromFavoritesItems()             //Observe Delete Or Remove Item From Favorites
        observeAddedToFavoritesItems()                 //Observe Add Item From Favorites

        return binding.root
    }

    private fun observeAddedToFavoritesItems() {
        viewModel.addedToFavorites.observe(viewLifecycleOwner) {
            it?.let { productsRecyclerAdapter.setFavoriteItem(it); productsRecyclerAdapter.notifyDataSetChanged() }
        }
    }

    private fun observeDeletedFromFavoritesItems() {
        viewModel.deleteFromFavorites.observe(viewLifecycleOwner){
            it?.let { productsRecyclerAdapter.removeFavoriteItem(it)  ; productsRecyclerAdapter.notifyDataSetChanged()}
        }
    }

    private fun setOnClickOnBackIcon() {
        binding.backCardSubCategoryFragment.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack() //Pop Back To Previous Fragment
        }
    }

    private fun observeErrorMessage() {
        viewModel.error.observe(viewLifecycleOwner) {
            it?.let {
                ToastyUtil.errorToasty(context!!, it, Toast.LENGTH_SHORT) //Toast Error Message
                viewModel.errorMessage.value = null
            }
        }
    }

    private fun setOnClickOnRecyclerItem() {
        productsRecyclerAdapter.setOnItemClickListener(object : ProductsSubExploreRecyclerAdapter.OnClickOnItem{
            override fun onClick1(product: Product) {
                //Pass Product To productDetailsFragment
                val args = Bundle()
                args.putSerializable(PRODUCT, product)
                val productDetailsFragment = ProductDetailsFragment()
                productDetailsFragment.arguments = args
                val transaction = activity!!.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.flFragment, productDetailsFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }


        })
    }

    private fun setOnClickOnFavoriteOfRecyclerItem() {
        productsRecyclerAdapter.setOnItemClickFavoriteListener(object : ProductsSubExploreRecyclerAdapter.OnClickOnItemFavorite{
            override fun onClick1(id: String, isFavorite: Boolean) {
                viewModel.editFavorite(id,isFavorite)
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SubCategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SubCategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}