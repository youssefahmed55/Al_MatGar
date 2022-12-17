package com.example.e_commerce.ui.homemarket.subcategory.productdetails

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.e_commerce.R
import com.example.e_commerce.adapters.SliderAdapterProductDetails
import com.example.e_commerce.databinding.BottomsheetquantityBinding
import com.example.e_commerce.databinding.FragmentProductDetailsBinding
import com.example.e_commerce.ui.homemarket.cart.cart.CartFragment
import com.example.e_commerce.utils.ToastyUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {
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
    private lateinit var binding : FragmentProductDetailsBinding
    private val viewModel : ProductDetailsViewModel by viewModels()
    private lateinit var bindingBottomSheetQuantityBinding: BottomsheetquantityBinding
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private val sliderAdapter : SliderAdapterProductDetails by lazy { SliderAdapterProductDetails() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity!!.findViewById<RelativeLayout>(R.id.relative1_homeActivity).visibility = View.GONE     //Set Visibility Of Activity's RelativeLayout GONE
        activity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.GONE //Set Visibility Of BottomNavigationView GONE
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_product_details, container, false) //Initialize binding
        bindingBottomSheetQuantityBinding = DataBindingUtil.inflate(inflater,R.layout.bottomsheetquantity, container, false) //Initialize bindingBottomSheetQuantityBinding
        binding.lifecycleOwner = this         //Set lifecycleOwner
        binding.viewModel = viewModel         //Set Variable Of ViewModel (DataBinding)
        binding.sliderAdapter = sliderAdapter //Set Variable Of sliderAdapter (DataBinding)
        inti()                                //Initialize Variables
        observeErrorMessage()                 //Observe Error
        setOnClickOnBackIcon()                //Set On Click On Back Icon
        setOnClickOnQTY()                     //Set On Click On QTY
        setOnClickShopCard()                  //Set On Click On Shop Card

        return binding.root
    }

    private fun setOnClickShopCard() {
        binding.shopCardProductDetailsFragment.setOnClickListener {
            activity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE    //Set Visibility Of BottomNavigationView VISIBLE
            activity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).selectedItemId = R.id.cart   //Set Selected Item
            activity!!.findViewById<TextView>(R.id.title_homeActivity).text = getString(R.string.cart)                              //Set Text Of Title
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.flFragment, CartFragment()).commit()//Replace CartFragment
        }
    }

    private fun setOnClickOnQTY() {
        val textViewNumbers = mutableListOf<TextView>()
        bindingBottomSheetQuantityBinding.apply {textViewNumbers.addAll(mutableListOf(oneBottomSheetQuantity,twoBottomSheetQuantity,threeBottomSheetQuantity,fourBottomSheetQuantity,fiveBottomSheetQuantity))  }

        binding.QTYProductDetailsFragment.setOnClickListener {
            bindingBottomSheetQuantityBinding.apply {
                oneBottomSheetQuantity.setOnClickListener { binding.QTYNumberProductDetailsFragment.text = "1" ; changeBackGroundColors(oneBottomSheetQuantity,textViewNumbers)}
                twoBottomSheetQuantity.setOnClickListener { binding.QTYNumberProductDetailsFragment.text = "2" ; changeBackGroundColors(twoBottomSheetQuantity,textViewNumbers)}
                threeBottomSheetQuantity.setOnClickListener { binding.QTYNumberProductDetailsFragment.text = "3" ; changeBackGroundColors(threeBottomSheetQuantity,textViewNumbers)}
                fourBottomSheetQuantity.setOnClickListener { binding.QTYNumberProductDetailsFragment.text = "4" ; changeBackGroundColors(fourBottomSheetQuantity,textViewNumbers)}
                fiveBottomSheetQuantity.setOnClickListener { binding.QTYNumberProductDetailsFragment.text = "5" ; changeBackGroundColors(fiveBottomSheetQuantity,textViewNumbers)}
                closeBottomSheetQuantity.setOnClickListener { bottomSheetDialog.dismiss() }
            }
            bottomSheetDialog.setContentView(bindingBottomSheetQuantityBinding.root)
            bottomSheetDialog.show()
        }
    }
    //Change Back Ground Color Of Quantity Numbers
    private fun changeBackGroundColors(numberBottomSheetQuantity: TextView, textViewNumbers: MutableList<TextView>) {
        textViewNumbers.forEach {
              if(numberBottomSheetQuantity.text.toString() != it.text.toString()) {
                  it.setBackgroundResource(R.drawable.shape1)
                  it.setTextColor(Color.BLACK)
              }else{
                  it.setBackgroundResource(R.drawable.shape2)
                  it.setTextColor(Color.WHITE)
              }
              }
    }

    private fun inti() {
        //Initialize bottomSheetDialog
        bottomSheetDialog = BottomSheetDialog(context!!, R.style.AppBottomSheetDialogTheme)
    }

    private fun observeErrorMessage() {
        viewModel.error.observe(viewLifecycleOwner) {
            it?.let {
                ToastyUtil.errorToasty(context!!, it, Toast.LENGTH_SHORT) //Toast Error Message
                viewModel.errorMessage.value = null
            }
        }
    }

    override fun onDetach() {
        activity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE //Set Visibility Of BottomNavigationView VISIBLE
        super.onDetach()
    }

    private fun setOnClickOnBackIcon() {
        binding.backCardProductDetailsFragment.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack() //Pop Back To Previous Fragment
        }
    }
}