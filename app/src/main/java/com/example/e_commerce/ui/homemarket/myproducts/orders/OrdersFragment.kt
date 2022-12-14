package com.example.e_commerce.ui.homemarket.myproducts.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.e_commerce.Constants.PRODUCT
import com.example.e_commerce.R
import com.example.e_commerce.adapters.OrdersMerchantRecyclerAdapter
import com.example.e_commerce.databinding.FragmentOrdersBinding
import com.example.e_commerce.ui.homemarket.subcategory.productdetails.ProductDetailsFragment
import com.example.e_commerce.utils.ToastyUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrdersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class OrdersFragment : Fragment() {
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
    private lateinit var binding : FragmentOrdersBinding
    private val ordersMerchantRecyclerAdapter : OrdersMerchantRecyclerAdapter by lazy { OrdersMerchantRecyclerAdapter() }
    private val viewModel : OrdersViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity!!.findViewById<RelativeLayout>(R.id.relative1_homeActivity).visibility = View.GONE         //Set Activity's RelativeLayout GONE
        activity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE  //Set Visibility Of BottomNavigationView VISIBLE
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_orders, container, false) //Initialize binding
        binding.lifecycleOwner = this                    //Set lifecycleOwner
        binding.viewModel = viewModel                    //Set Variable Of ViewModel (DataBinding)
        setOnClickOnRecyclerItem()                       //Set On Click On Recycler Item
        setOnClickOnCancelRecyclerItem()                 //Set On Click On Cancel From Recycler Item
        setOnClickOnDoneRecyclerItem()                   //Set On Click On Done From Recycler Item
        binding.adapter = ordersMerchantRecyclerAdapter  //Set Variable Of adapter (DataBinding)
        observeErrorMessage()                            //Observe Error
        observeProduct()                                 //Observe Product If User Clicked On Recycler Item
        setOnClickOnBackIcon()                           //Set On Click On Back Icon
        return binding.root
    }



    private fun observeProduct() {
        viewModel.liveDataProduct.observe(viewLifecycleOwner){
            it?.let {
                //Pass Product To ProductDetailsFragment
                viewModel.mutableLiveDataProduct.value = null
                val args = Bundle()
                args.putSerializable(PRODUCT, it)
                val productDetailsFragment = ProductDetailsFragment()
                productDetailsFragment.arguments = args
                val transaction = activity!!.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.flFragment, productDetailsFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }

        }
    }
    private fun setOnClickOnRecyclerItem() {
        ordersMerchantRecyclerAdapter.setOnItemClickListener(object : OrdersMerchantRecyclerAdapter.OnClickOnItem{
            override fun onClick1(productId: String) {
                viewModel.getProduct(productId)
            }
        })
    }

    private fun setOnClickOnDoneRecyclerItem() {
        ordersMerchantRecyclerAdapter.setOnDoneClickListener(object : OrdersMerchantRecyclerAdapter.OnClickOnDone{
            override fun onClick1(orderId: String, customerId: String) {
                val builder = AlertDialog.Builder(context!!)
                builder.setMessage(getString(R.string.Do_You_Want_To_Complete_This_Order))
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.Yes)) { dialog, _ ->
                        viewModel.completeOrder(orderId,customerId)
                        dialog.dismiss()
                    }
                    .setNegativeButton(getString(R.string.No)) { dialog, _ ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }
        })
    }

    private fun setOnClickOnCancelRecyclerItem() {
        ordersMerchantRecyclerAdapter.setOnCancelClickListener(object : OrdersMerchantRecyclerAdapter.OnClickOnCancel{
            override fun onClick1(orderId: String, customerId: String) {
                val builder = AlertDialog.Builder(context!!)
                builder.setMessage(getString(R.string.Do_You_Want_To_Cancel_This_Order))
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.Yes)) { dialog, _ ->
                        viewModel.cancelOrder(orderId,customerId)
                        dialog.dismiss()
                    }
                    .setNegativeButton(getString(R.string.No)) { dialog, _ ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }
        })
    }
    private fun setOnClickOnBackIcon() {
        binding.backCardOrdersFragment.setOnClickListener {
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrdersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrdersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}