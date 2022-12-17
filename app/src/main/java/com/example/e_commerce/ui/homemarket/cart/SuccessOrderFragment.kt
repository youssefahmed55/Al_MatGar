package com.example.e_commerce.ui.homemarket.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentSuccessOrderBinding
import com.example.e_commerce.ui.homemarket.home.HomeFragment
import com.example.e_commerce.utils.SharedPrefsUtil
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SuccessOrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SuccessOrderFragment : Fragment() {
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
   private lateinit var binding : FragmentSuccessOrderBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.GONE            //Set Visibility Of BottomNavigationView GONE
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_success_order, container, false) //Initialize binding
        binding.lifecycleOwner = this                          //Set lifecycleOwner
        binding.image = SharedPrefsUtil.getImageUrl(context!!) //Set Variable Of image (DataBinding)
        setOnClickOnBack()                                     //Set On Press Back
        setOnClickOnBackToHomeButton()                         //Set On Click On Back To Home Button
        return binding.root
    }

    private fun setOnClickOnBackToHomeButton() {
        binding.backToHomeButtonSuccessOrderFragment.setOnClickListener {
            replaceHomeFragment() //Replace HomeFragment
        }
    }
   private fun replaceHomeFragment(){
       activity!!.findViewById<TextView>(R.id.title_homeActivity).text = ""
       activity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).selectedItemId = R.id.home     //Set Selected Item
       activity!!.supportFragmentManager.beginTransaction().replace(R.id.flFragment, HomeFragment()).commit()  //Replace HomeFragment()
   }


    private fun setOnClickOnBack() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    replaceHomeFragment() //Replace HomeFragment
                }
            }
        activity!!.onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SuccessOrderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SuccessOrderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}