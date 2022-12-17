package com.example.e_commerce.ui.homemarket.cart.cart

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.e_commerce.Constants.LIST_COUNTS
import com.example.e_commerce.Constants.LIST_PRODUCTS
import com.example.e_commerce.Constants.PRODUCT
import com.example.e_commerce.R
import com.example.e_commerce.adapters.ProductsInCartRecyclerAdapter
import com.example.e_commerce.databinding.FragmentCartBinding
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.ui.homemarket.cart.shipto.ShipToFragment
import com.example.e_commerce.ui.homemarket.home.HomeFragment
import com.example.e_commerce.ui.homemarket.subcategory.productdetails.ProductDetailsFragment
import com.example.e_commerce.utils.ToastyUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CartFragment : Fragment() {
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
    private lateinit var binding: FragmentCartBinding
    private val viewModel : CartViewModel by viewModels()
    private val productsInCartRecyclerAdapter : ProductsInCartRecyclerAdapter by lazy { ProductsInCartRecyclerAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity!!.findViewById<RelativeLayout>(R.id.relative1_homeActivity).visibility = View.VISIBLE    //Set Activity's RelativeLayout VISIBLE
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_cart, container, false) //Initialize binding
        binding.lifecycleOwner = this                    //Set lifecycleOwner
        setOnClickOnDeleteIconItemOfRecycler()           //Set On Click On Delete Icon From Recycler Item
        setOnClickOnItemOfRecycler()                     //Set On Click On Recycler Item
        setOnClickOnPlusOfRecyclerItem()                 //Set On Click On + Icon From Recycler Item
        setOnClickOnMinusOfRecyclerItem()                //Set On Click On - Icon From Recycler Item
        setOnClickOnNextButton()                         //Set On Click On Next Button
        binding.viewModel = viewModel                    //Set Variable Of ViewModel (DataBinding)
        binding.adapter = productsInCartRecyclerAdapter  //Set Variable Of adapter (DataBinding)
        setOnClickOnBack()                               //Set On Press Back
        setOnClickOnStartShoppingButton()                //Set On Click On Start Shopping Button
        observeErrorMessage()                            //Observe Error

        return binding.root
    }

    private fun setOnClickOnStartShoppingButton() {
        binding.startShoppingButtonCartFragment.setOnClickListener {
            activity!!.findViewById<TextView>(R.id.title_homeActivity).text = ""                                   //Set Text Of Title
            activity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).selectedItemId = R.id.home    //Set Selected Item Id To BottomNavigationView
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.flFragment, HomeFragment()).commit() //Replace HomeFragment
        }
    }

    private fun setOnClickOnNextButton() {
        binding.nextButtonCartFragment.setOnClickListener {
            //Pass List Of Products And List Of Counts To ShipToFragment
            val args = Bundle()
            val listOfProducts = productsInCartRecyclerAdapter.getList()
            val listOfCounts = productsInCartRecyclerAdapter.getListOfCount()
            if (listOfProducts.isNotEmpty()&&listOfCounts.isNotEmpty()) {
                args.putSerializable(LIST_PRODUCTS, listOfProducts as Serializable)
                args.putSerializable(LIST_COUNTS, listOfCounts as Serializable)
                val shipToFragment = ShipToFragment()
                shipToFragment.arguments = args
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.flFragment, shipToFragment).addToBackStack(null).commit()
            }
        }
    }

    private fun setOnClickOnMinusOfRecyclerItem() {
        productsInCartRecyclerAdapter.setOnMinusClickListener(object : ProductsInCartRecyclerAdapter.OnClickOnItemMinus{
            override fun onClickMinus(position: Int, count: Int) {
                if (count != 1){
                    productsInCartRecyclerAdapter.editCount(position,count-1)
                    productsInCartRecyclerAdapter.notifyDataSetChanged()
                }
            }


        })
    }

    private fun setOnClickOnPlusOfRecyclerItem() {
        productsInCartRecyclerAdapter.setOnPlusClickListener(object : ProductsInCartRecyclerAdapter.OnClickOnItemPlus{
            override fun onClickPlus(position: Int, count: Int) {
                if (count != 5){
                    productsInCartRecyclerAdapter.editCount(position,count+1)
                    productsInCartRecyclerAdapter.notifyDataSetChanged()
                }
            }


        })
    }

    private fun setOnClickOnBack() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {activity!!.finish()}
            }
        activity!!.onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun observeErrorMessage() {
        viewModel.error.observe(viewLifecycleOwner) {
            it?.let {
                ToastyUtil.errorToasty(context!!, it, Toast.LENGTH_SHORT) //Toast Error Message
                viewModel.errorMessage.value = null
            }
        }
    }

    private fun setOnClickOnDeleteIconItemOfRecycler() {
        productsInCartRecyclerAdapter.setOnDeleteClickListener(object : ProductsInCartRecyclerAdapter.OnClickOnItemDelete{
            override fun onClickDelete(id: String) {
                val builder = AlertDialog.Builder(context!!)
                builder.setMessage(getString(R.string.Are_you_sure_that_you_want_to_delete_it))
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.Yes)) { dialog, _ ->
                        viewModel.deleteProductFromInCard(id)
                        dialog.dismiss()
                    }
                    .setNegativeButton(getString(R.string.No)) { dialog, _ ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()  //Show Alert Dialog
            }

        })
    }

    private fun setOnClickOnItemOfRecycler() {
        productsInCartRecyclerAdapter.setOnItemClickListener(object : ProductsInCartRecyclerAdapter.OnClickOnItem{
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}