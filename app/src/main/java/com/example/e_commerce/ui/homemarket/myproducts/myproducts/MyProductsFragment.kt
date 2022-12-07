package com.example.e_commerce.ui.homemarket.myproducts.myproducts

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.e_commerce.R
import com.example.e_commerce.adapters.ProductsMerchantRecyclerAdapter
import com.example.e_commerce.databinding.FragmentMyProductsBinding
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.ui.homemarket.myproducts.addproduct.AddProductFragment
import com.example.e_commerce.ui.homemarket.subcategory.productdetails.ProductDetailsFragment
import com.example.e_commerce.utils.ToastyUtil
import dagger.hilt.android.AndroidEntryPoint


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyProductsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MyProductsFragment : Fragment() {
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
    private lateinit var binding : FragmentMyProductsBinding
    private val viewModel: MyProductsViewModel by lazy { ViewModelProvider(this)[MyProductsViewModel::class.java] }
    private val productsMerchantRecyclerAdapter : ProductsMerchantRecyclerAdapter by lazy { ProductsMerchantRecyclerAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_my_products, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        activity!!.findViewById<RelativeLayout>(R.id.relative1_homeActivity).visibility = View.VISIBLE
        setOnClickOnItemOfRecycler()
        setOnClickOnDeleteIconItemOfRecycler()

        binding.productsMerchantRecyclerAdapter = productsMerchantRecyclerAdapter
        setOnClickOnBack()
        observeErrorMessage()
        setOnClickOnAddButton()

        return binding.root
    }

    private fun setOnClickOnBack() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {activity!!.finish()}
            }
        activity!!.onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }


    override fun onResume() {
        super.onResume()
        viewModel.refreshData()
    }

    private fun observeErrorMessage() {
        viewModel.error.observe(viewLifecycleOwner) {
            it?.let {
                ToastyUtil.errorToasty(context!!, it, Toast.LENGTH_SHORT)
                viewModel._error.value = null
            }
        }
    }

    private fun setOnClickOnDeleteIconItemOfRecycler() {
        productsMerchantRecyclerAdapter.setOnDeleteClickListener(object : ProductsMerchantRecyclerAdapter.OnClickOnItemDelete{
            override fun onClickDelete(id: String, list: List<String>?) {
                val builder = AlertDialog.Builder(context!!)
                builder.setMessage("Are you sure that you want to delete it ?")
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.Yes)) { dialog, _ ->
                        viewModel.deleteProduct(id,list)
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

    private fun setOnClickOnItemOfRecycler() {
        productsMerchantRecyclerAdapter.setOnItemClickListener(object : ProductsMerchantRecyclerAdapter.OnClickOnItem{
            override fun onClick1(product: Product) {
                val args = Bundle()
                args.putSerializable("product", product)
                val productDetailsFragment = ProductDetailsFragment()
                productDetailsFragment.arguments = args
                val transaction = activity!!.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.flFragment, productDetailsFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }

        })
    }

    private fun setOnClickOnAddButton() {
        binding.addFloatingButtonMyProductsFragment.setOnClickListener {
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, AddProductFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyProductsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyProductsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}