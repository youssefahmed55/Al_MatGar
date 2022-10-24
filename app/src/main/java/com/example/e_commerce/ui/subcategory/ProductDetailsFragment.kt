package com.example.e_commerce.ui.subcategory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.e_commerce.R
import com.example.e_commerce.adapters.SliderAdapter
import com.example.e_commerce.databinding.FragmentProductDetailsBinding
import com.example.e_commerce.pojo.Product
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.smarteist.autoimageslider.SliderView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var product : Product? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            product = it.getSerializable("product") as Product?
        }
    }
    private lateinit var binding : FragmentProductDetailsBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_product_details, container, false)
        binding.lifecycleOwner = this
        setOnClickOnBackIcon()

        bottomNavigationView = activity!!.findViewById(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.GONE

        if (product!=null) setSliderAdapter(product!!.images)
        binding.product = product
        return binding.root
    }

    override fun onDestroy() {
        bottomNavigationView.visibility = View.VISIBLE
        super.onDestroy()
    }

    private fun setOnClickOnBackIcon() {
        binding.backArrowProductDetailsFragment.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }
    }


    private fun setSliderAdapter(list: List<String>) {
        val sliderAdapter = SliderAdapter(list)
        // on below line we are setting auto cycle direction
        // for our slider view from left to right.
        binding.imageSliderProductDetailsFragment.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH

        // on below line we are setting adapter for our slider.
        binding.imageSliderProductDetailsFragment.setSliderAdapter(sliderAdapter)

        // on below line we are setting scroll time
        // in seconds for our slider view.
        binding.imageSliderProductDetailsFragment.scrollTimeInSec = 3

        // on below line we are setting auto cycle
        // to true to auto slide our items.
        binding.imageSliderProductDetailsFragment.isAutoCycle = true

        // on below line we are calling start
        // auto cycle to start our cycle.
        binding.imageSliderProductDetailsFragment.startAutoCycle()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProductDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}