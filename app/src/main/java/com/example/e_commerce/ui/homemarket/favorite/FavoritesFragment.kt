package com.example.e_commerce.ui.homemarket.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.e_commerce.Constants.PRODUCT
import com.example.e_commerce.R
import com.example.e_commerce.adapters.ProductsSubExploreRecyclerAdapter
import com.example.e_commerce.databinding.FragmentFavoritesBinding
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.ui.homemarket.subcategory.productdetails.ProductDetailsFragment
import com.example.e_commerce.utils.ToastyUtil
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


@AndroidEntryPoint
class FavoritesFragment : Fragment() {
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


    private lateinit var binding : FragmentFavoritesBinding
    private val viewModel : FavoritesFragmentViewModel by viewModels()
    private val productsRecyclerAdapter : ProductsSubExploreRecyclerAdapter by lazy { ProductsSubExploreRecyclerAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity!!.findViewById<RelativeLayout>(R.id.relative1_homeActivity).visibility = View.GONE //Set Activity's RelativeLayout GONE
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_favorites, container, false) //Initialize binding
        binding.lifecycleOwner = this                //Set lifecycleOwner
        binding.viewModel = viewModel                //Set Variable Of ViewModel (DataBinding)
        setOnClickOnRecyclerItem()                   //Set On Click On Recycler Item
        setOnClickOnFavoriteOfRecyclerItem()         //Set On Click On Favorite Recycler Item
        productsRecyclerAdapter.setContext(context!!)//Set Context To Recycler Adapter
        binding.adapter = productsRecyclerAdapter    //Set Variable Of adapter (DataBinding)
        setOnClickOnBackIcon()                       //Set On Click On Back Icon
        observeErrorMessage()                        //Observe Error
        observeAddedToFavoritesItems()               //Observe Add Item From Favorites
        observeDeletedFromFavoritesItems()           //Observe Delete Or Remove Item From Favorites
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
        binding.backCardFavoritesFragment.setOnClickListener {
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
                //Pass Product To ProductDetailsFragment
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
}