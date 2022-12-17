package com.example.e_commerce.ui.homemarket.cart.shipto

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
import androidx.lifecycle.lifecycleScope
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentShipToBinding
import com.example.e_commerce.ui.homemarket.account.AccountFragment
import com.example.e_commerce.ui.homemarket.cart.SuccessOrderFragment
import com.example.e_commerce.utils.ToastyUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


@AndroidEntryPoint
class ShipToFragment : Fragment() {
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
    private lateinit var binding : FragmentShipToBinding
    private val viewModel : ShipToViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity!!.findViewById<RelativeLayout>(R.id.relative1_homeActivity).visibility = View.GONE          //Set Activity's RelativeLayout GONE
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_ship_to, container, false) //Initialize binding
        binding.lifecycleOwner = this    //Set lifecycleOwner
        binding.viewModel = viewModel    //Set Variable Of ViewModel (DataBinding)
        setOnClickOnBackIcon()           //Set On Click On Back Icon
        setOnClickOnEditButton()         //Set On Click On Edit Button
        render()                         //render states From ViewModel
        return binding.root
    }

    private fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.states.collect{
                when(it){
                    is DefaultStates.Success -> {
                        ToastyUtil.successToasty(context!!,it.toastMessage,Toast.LENGTH_SHORT) //Toast Successful Message
                        activity!!.supportFragmentManager.beginTransaction().replace(R.id.flFragment, SuccessOrderFragment()).addToBackStack(null).commit()
                    }
                    is DefaultStates.Error -> {
                        ToastyUtil.errorToasty(context!!,it.error,Toast.LENGTH_SHORT)          //Toast Error Message
                    }

                    else -> {}
                }
            }
        }
    }

    private fun setOnClickOnEditButton() {
        binding.editButtonShipToFragment.setOnClickListener {
            activity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).selectedItemId = R.id.account     //Set SelectedItemId To BottomNavigationView
            activity!!.findViewById<TextView>(R.id.title_homeActivity).text = getString(R.string.account)                                //Set Text To Title
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.flFragment, AccountFragment()).commit()  //Replace AccountFragment
        }
    }

    private fun setOnClickOnBackIcon() {
        binding.backCardShipToFragment.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack() //Pop Back To Previous Fragment
        }
    }
}