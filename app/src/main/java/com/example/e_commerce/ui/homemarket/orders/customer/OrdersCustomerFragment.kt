package com.example.e_commerce.ui.homemarket.orders.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.e_commerce.R
import com.example.e_commerce.adapters.OrdersCustomerRecyclerAdapter
import com.example.e_commerce.databinding.FragmentOrdersBinding
import com.example.e_commerce.databinding.FragmentOrdersCustomerBinding
import com.example.e_commerce.ui.homemarket.subcategory.productdetails.ProductDetailsFragment
import com.example.e_commerce.utils.ToastyUtil
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrdersCustomerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class OrdersCustomerFragment : Fragment() {
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
    private lateinit var binding : FragmentOrdersCustomerBinding
    private val viewModel : OrdersCustomerViewModel by viewModels()
    private val ordersCustomerRecyclerAdapter : OrdersCustomerRecyclerAdapter by lazy { OrdersCustomerRecyclerAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity!!.findViewById<RelativeLayout>(R.id.relative1_homeActivity).visibility = View.GONE
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_orders_customer, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setOnClickOnItemOfRecycler()
        binding.adapter = ordersCustomerRecyclerAdapter
        setOnClickOnBackIcon()
        observeProduct()
        observeErrorMessage()
        return binding.root
    }

    private fun observeProduct() {
        viewModel.liveDataProduct.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.mutableLiveDataProduct.value = null
                val args = Bundle()
                args.putSerializable("product", it)
                val productDetailsFragment = ProductDetailsFragment()
                productDetailsFragment.arguments = args
                val transaction = activity!!.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.flFragment, productDetailsFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }

        })
    }

    private fun observeErrorMessage() {
        viewModel.error.observe(viewLifecycleOwner) {
            it?.let {
                ToastyUtil.errorToasty(context!!, it, Toast.LENGTH_SHORT)
                viewModel._error.value = null
            }
        }
    }

    private fun setOnClickOnItemOfRecycler() {
        ordersCustomerRecyclerAdapter.setOnItemClickListener(object : OrdersCustomerRecyclerAdapter.OnClickOnItem{
            override fun onClick1(productId: String) {
                viewModel.getProduct(productId)
            }
        })
    }
    private fun setOnClickOnBackIcon() {
        binding.backCardOrdersCustomer.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrdersCustomerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrdersCustomerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}