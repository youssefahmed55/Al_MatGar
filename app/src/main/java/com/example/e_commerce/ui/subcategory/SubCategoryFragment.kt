package com.example.e_commerce.ui.subcategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.e_commerce.R
import com.example.e_commerce.adapters.ProductsExploreRecyclerAdapter
import com.example.e_commerce.databinding.FragmentSubCategoryBinding
import com.example.e_commerce.pojo.Category
import com.example.e_commerce.pojo.Product

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SubCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SubCategoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var imageUrl: String? = null
    private var category : Category ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            imageUrl = it.getString("imageUrl")
            category = it.getSerializable("catName") as Category?
        }
    }
    private lateinit var binding : FragmentSubCategoryBinding
    private val productsRecyclerAdapter : ProductsExploreRecyclerAdapter by lazy { ProductsExploreRecyclerAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_sub_category, container, false)
        binding.lifecycleOwner = this

        if (category != null){binding.categoryName = category!!.name }
        if (imageUrl != null){binding.imageUrl = imageUrl}
        setOnClickOnBackIcon()

        val listOfProducts = listOf<Product>(Product(55,"FoodFoodFoodFoodFoodFood","Food","dddd",55.00, listOf("https://imageproxy.wolt.com/venue/5e9ed940634ff8fe31e88912/82bf3a34-83d5-11ea-b0a6-0a586469ca09_food_coma8_menu.jpg"))
            ,Product(70,"Food","Food","dddd",60.00, listOf("https://imageproxy.wolt.com/venue/5e9ed940634ff8fe31e88912/82bf3a34-83d5-11ea-b0a6-0a586469ca09_food_coma8_menu.jpg"))
            ,Product(80,"Food","Food","dddd",70.00, listOf("https://imageproxy.wolt.com/venue/5e9ed940634ff8fe31e88912/82bf3a34-83d5-11ea-b0a6-0a586469ca09_food_coma8_menu.jpg"))
            ,Product(90,"Food","Food","dddd",80.00, listOf("https://imageproxy.wolt.com/venue/5e9ed940634ff8fe31e88912/82bf3a34-83d5-11ea-b0a6-0a586469ca09_food_coma8_menu.jpg"))
            ,Product(100,"Food","Food","dddd",55.00, listOf("https://imageproxy.wolt.com/venue/5e9ed940634ff8fe31e88912/82bf3a34-83d5-11ea-b0a6-0a586469ca09_food_coma8_menu.jpg")))


        productsRecyclerAdapter.setOnItemClickListener(object : ProductsExploreRecyclerAdapter.OnClickOnItem{
            override fun onClick1(product: Product) {
                //TODO("Not yet implemented")
            }


        })
        setProductRecyclerAdapter(listOfProducts)

        return binding.root
    }

    private fun setProductRecyclerAdapter(listOfProducts: List<Product>) {
        productsRecyclerAdapter.setList(listOfProducts)
        binding.recyclerSubCategoryFragment.adapter = productsRecyclerAdapter
    }

    private fun setOnClickOnBackIcon() {
      binding.backIconSubCategoryFragment.setOnClickListener {
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
         * @return A new instance of fragment SubCategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SubCategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}