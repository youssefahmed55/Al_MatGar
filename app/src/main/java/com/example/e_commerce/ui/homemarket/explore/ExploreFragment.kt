package com.example.e_commerce.ui.homemarket.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.e_commerce.R
import com.example.e_commerce.adapters.ProductsExploreRecyclerAdapter
import com.example.e_commerce.adapters.ProductsHomeRecyclerAdapter
import com.example.e_commerce.databinding.FragmentExploreBinding
import com.example.e_commerce.pojo.Product

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExploreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExploreFragment : Fragment() {
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


    private lateinit var binding : FragmentExploreBinding
    private val searchRecyclerAdapter : ProductsExploreRecyclerAdapter by lazy { ProductsExploreRecyclerAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_explore, container, false)

        val listOfProductSearch = listOf<Product>(
            Product("55","BeautyCounterBeautyCounter","","BeautyCounter","dddd",55.00, listOf("https://images.beautycounter.com/product-images%2F100000182%2Fimgs%2FAT_THE_RED_Y_LIP_DUO_PDP_01.jpg"),true,44.0,1)
            , Product("56","Beauty", "","BeautyCounter","dddd",55.00, listOf("https://images.beautycounter.com/product-images%2F100000182%2Fimgs%2FAT_THE_RED_Y_LIP_DUO_PDP_01.jpg"),true,44.0,1)
            , Product("57","BeautyCounter","","BeautyCounter","dddd",55.00, listOf("https://images.beautycounter.com/product-images%2F100000182%2Fimgs%2FAT_THE_RED_Y_LIP_DUO_PDP_01.jpg"),true,44.0,1)
            , Product("58","BeautyCounter","","BeautyCounter","dddd",55.00, listOf("https://images.beautycounter.com/product-images%2F100000182%2Fimgs%2FAT_THE_RED_Y_LIP_DUO_PDP_01.jpg"),true,44.0,1)
            , Product("59","BeautyCounter","","BeautyCounter","dddd",55.00, listOf("https://images.beautycounter.com/product-images%2F100000182%2Fimgs%2FAT_THE_RED_Y_LIP_DUO_PDP_01.jpg"),true,44.0,1)
        )

        searchRecyclerAdapter.setOnItemClickListener(object : ProductsExploreRecyclerAdapter.OnClickOnItem{
            override fun onClick1(product: Product) {
                //TODO("Not yet implemented")
            }


        })
        setSearchRecyclerAdapter(listOfProductSearch)

        return binding.root
    }

    private fun setSearchRecyclerAdapter(listOfProductSearch: List<Product>) {
        searchRecyclerAdapter.setList(listOfProductSearch)
        binding.recyclerExploreFragment.adapter = searchRecyclerAdapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExploreFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExploreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}