package com.example.e_commerce.ui.homemarket.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.e_commerce.R
import com.example.e_commerce.adapters.CategoriesRecyclerAdapter
import com.example.e_commerce.adapters.ProductsHomeRecyclerAdapter
import com.example.e_commerce.adapters.SliderAdapter
import com.example.e_commerce.databinding.FragmentHomeBinding
import com.example.e_commerce.pojo.Category
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.ui.subcategory.SubCategoryFragment
import com.smarteist.autoimageslider.SliderView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
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
    private lateinit var binding:FragmentHomeBinding
    private val categoriesRecyclerAdapter : CategoriesRecyclerAdapter by lazy { CategoriesRecyclerAdapter() }
    private val beautyRecyclerAdapter : ProductsHomeRecyclerAdapter by lazy { ProductsHomeRecyclerAdapter() }
    private val foodRecyclerAdapter : ProductsHomeRecyclerAdapter by lazy { ProductsHomeRecyclerAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)

        val list = listOf<String>("https://media.centrepointstores.com/i/centrepoint/SP_Offers_Block06MAR18.jpg","https://media.centrepointstores.com/i/centrepoint/SP_Offers_Block02MAR18.jpg","https://media.centrepointstores.com/i/centrepoint/SP_Offers_Block01MAR18.jpg")
        setSliderAdapter(list)

        val listOfCat = listOf<Category>(Category(1,"Beauty","https://www.npd.com/wp-content/uploads/2021/06/Beauty-Industry-holiday.jpg")
                                        ,Category(2,"Clothes","https://www.popsci.com/uploads/2022/03/02/aviv-rachmadian-7F7kEHj72MQ-unsplash-scaled.jpg")
                                        ,Category(3,"Food","https://images.squarespace-cdn.com/content/v1/53b839afe4b07ea978436183/1608506169128-S6KYNEV61LEP5MS1UIH4/traditional-food-around-the-world-Travlinmad.jpg")
                                        ,Category(4,"HouseWare","https://t4.ftcdn.net/jpg/02/57/96/75/360_F_257967574_bnpqOyc6wEe38VU5rclWub3BphOB2y6T.jpg"))

        categoriesRecyclerAdapter.setOnItemClickListener(object : CategoriesRecyclerAdapter.OnClickOnItem{
            override fun onClick1(cat: Category) {
                val args = Bundle()
                args.putSerializable("catName", cat)
                args.putString("imageUrl" , "https://thumbs.dreamstime.com/b/nice-to-talk-smart-person-indoor-shot-attractive-interesting-caucasian-guy-smiling-broadly-nice-to-112345489.jpg")
                val subCategoryFragment = SubCategoryFragment()
                subCategoryFragment.arguments = args
                val transaction = activity!!.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.flFragment, subCategoryFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })
        setCategoriesRecyclerAdapter(listOfCat)



        val listOfProductBeauty = listOf<Product>(Product(55,"BeautyCounterBeautyCounter","BeautyCounter","dddd",55.00, listOf("https://images.beautycounter.com/product-images%2F100000182%2Fimgs%2FAT_THE_RED_Y_LIP_DUO_PDP_01.jpg"))
                                            ,Product(56,"Beauty","BeautyCounter","dddd",55.00, listOf("https://images.beautycounter.com/product-images%2F100000182%2Fimgs%2FAT_THE_RED_Y_LIP_DUO_PDP_01.jpg"))
                                            ,Product(57,"BeautyCounter","BeautyCounter","dddd",55.00, listOf("https://images.beautycounter.com/product-images%2F100000182%2Fimgs%2FAT_THE_RED_Y_LIP_DUO_PDP_01.jpg"))
                                            ,Product(58,"BeautyCounter","BeautyCounter","dddd",55.00, listOf("https://images.beautycounter.com/product-images%2F100000182%2Fimgs%2FAT_THE_RED_Y_LIP_DUO_PDP_01.jpg"))
                                            ,Product(59,"BeautyCounter","BeautyCounter","dddd",55.00, listOf("https://images.beautycounter.com/product-images%2F100000182%2Fimgs%2FAT_THE_RED_Y_LIP_DUO_PDP_01.jpg")))

        beautyRecyclerAdapter.setOnItemClickListener(object : ProductsHomeRecyclerAdapter.OnClickOnItem{
            override fun onClick1(product: Product) {
                //TODO("Not yet implemented")
            }


        })
        setBeautyRecyclerAdapter(listOfProductBeauty)


        val listOfProductFood = listOf<Product>(Product(55,"FoodFoodFoodFoodFoodFood","Food","dddd",55.00, listOf("https://imageproxy.wolt.com/venue/5e9ed940634ff8fe31e88912/82bf3a34-83d5-11ea-b0a6-0a586469ca09_food_coma8_menu.jpg"))
            ,Product(70,"Food","Food","dddd",60.00, listOf("https://imageproxy.wolt.com/venue/5e9ed940634ff8fe31e88912/82bf3a34-83d5-11ea-b0a6-0a586469ca09_food_coma8_menu.jpg"))
            ,Product(80,"Food","Food","dddd",70.00, listOf("https://imageproxy.wolt.com/venue/5e9ed940634ff8fe31e88912/82bf3a34-83d5-11ea-b0a6-0a586469ca09_food_coma8_menu.jpg"))
            ,Product(90,"Food","Food","dddd",80.00, listOf("https://imageproxy.wolt.com/venue/5e9ed940634ff8fe31e88912/82bf3a34-83d5-11ea-b0a6-0a586469ca09_food_coma8_menu.jpg"))
            ,Product(100,"Food","Food","dddd",55.00, listOf("https://imageproxy.wolt.com/venue/5e9ed940634ff8fe31e88912/82bf3a34-83d5-11ea-b0a6-0a586469ca09_food_coma8_menu.jpg")))

        foodRecyclerAdapter.setOnItemClickListener(object : ProductsHomeRecyclerAdapter.OnClickOnItem{
            override fun onClick1(product: Product) {
                //TODO("Not yet implemented")
            }


        })
        setFoodRecyclerAdapter(listOfProductFood)


        return binding.root
    }

    private fun setFoodRecyclerAdapter(listOfProductFood: List<Product>) {
        foodRecyclerAdapter.setList(listOfProductFood)
        binding.recyclerFoodHomeFragment.adapter = foodRecyclerAdapter
    }

    private fun setBeautyRecyclerAdapter(listOfProductBeauty: List<Product>) {
        beautyRecyclerAdapter.setList(listOfProductBeauty)
        binding.recyclerBeautyHomeFragment.adapter = beautyRecyclerAdapter
    }

    private fun setCategoriesRecyclerAdapter(listOfCat: List<Category>) {
       categoriesRecyclerAdapter.setList(listOfCat)
       binding.recyclerCategoriesHomeFragment.adapter = categoriesRecyclerAdapter
    }

    private fun setSliderAdapter(list: List<String>) {
       val sliderAdapter = SliderAdapter(list)
        // on below line we are setting auto cycle direction
        // for our slider view from left to right.
        binding.imageSliderHomeFragment.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH

        // on below line we are setting adapter for our slider.
        binding.imageSliderHomeFragment.setSliderAdapter(sliderAdapter)

        // on below line we are setting scroll time
        // in seconds for our slider view.
        binding.imageSliderHomeFragment.scrollTimeInSec = 3

        // on below line we are setting auto cycle
        // to true to auto slide our items.
        binding.imageSliderHomeFragment.isAutoCycle = true

        // on below line we are calling start
        // auto cycle to start our cycle.
        binding.imageSliderHomeFragment.startAutoCycle()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}