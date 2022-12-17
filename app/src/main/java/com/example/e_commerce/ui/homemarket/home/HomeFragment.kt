package com.example.e_commerce.ui.homemarket.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.e_commerce.Constants.CATEGORY_ID
import com.example.e_commerce.Constants.CATEGORY_NAME
import com.example.e_commerce.Constants.PRODUCT
import com.example.e_commerce.R
import com.example.e_commerce.adapters.CategoriesRecyclerAdapter
import com.example.e_commerce.adapters.ProductsHomeRecyclerAdapter
import com.example.e_commerce.adapters.SliderAdapterHome
import com.example.e_commerce.databinding.FragmentHomeBinding
import com.example.e_commerce.pojo.Category
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.ui.homemarket.subcategory.productdetails.ProductDetailsFragment
import com.example.e_commerce.ui.homemarket.subcategory.subcategory.SubCategoryFragment
import com.example.e_commerce.utils.ToastyUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
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
    private val electronicsRecyclerAdapter : ProductsHomeRecyclerAdapter by lazy { ProductsHomeRecyclerAdapter() }
    private val houseWareRecyclerAdapter : ProductsHomeRecyclerAdapter by lazy { ProductsHomeRecyclerAdapter() }
    private val sliderAdapter : SliderAdapterHome by lazy { SliderAdapterHome() }
    private val viewModel: HomeFragmentViewModel by lazy { ViewModelProvider(this)[HomeFragmentViewModel::class.java] }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity!!.findViewById<RelativeLayout>(R.id.relative1_homeActivity).visibility = View.VISIBLE     //Set Activity's RelativeLayout VISIBLE
        activity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE //Set Visibility Of BottomNavigationView VISIBLE
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)  //Initialize binding
        binding.lifecycleOwner = this                                  //Set lifecycleOwner
        setOnClickOnSliderImage()                                      //Set On Click On Slider Item Image
        setOnClickOnCategoriesItem()                                   //Set On Click On Categories Recycler Item
        setOnClickOnBeautyItem()                                       //Set On Click On Beauty Recycler Item
        setOnClickOnFoodItem()                                         //Set On Click On Food Recycler Item
        setOnClickOnElectronicsItem()                                  //Set On Click On Electronics Recycler Item
        setOnClickOnHouseWareItem()                                    //Set On Click On HouseWare Recycler Item
        binding.viewModel = viewModel                                  //Set Variable Of ViewModel (DataBinding)
        binding.sliderAdapter = sliderAdapter                          //Set Variable Of sliderAdapter (DataBinding)
        binding.categoriesRecyclerAdapter = categoriesRecyclerAdapter  //Set Variable Of categoriesRecyclerAdapter (DataBinding)
        binding.beautyRecyclerAdapter = beautyRecyclerAdapter          //Set Variable Of beautyRecyclerAdapter (DataBinding)
        binding.foodRecyclerAdapter = foodRecyclerAdapter              //Set Variable Of foodRecyclerAdapter (DataBinding)
        binding.electronicsRecyclerAdapter = electronicsRecyclerAdapter//Set Variable Of electronicsRecyclerAdapter (DataBinding)
        binding.houseWareRecyclerAdapter = houseWareRecyclerAdapter    //Set Variable Of houseWareRecyclerAdapter (DataBinding)
        observeErrorMessage()                                          //Observe Error
        observeResultOfClickedOnSliderImage()                          //Observe Result On Click On Slider Item Image
        setOnClickOnBack()                                             //Set On Press Back
        return binding.root
    }

    private fun observeResultOfClickedOnSliderImage() {
        viewModel.liveDataProduct.observe(viewLifecycleOwner){
            it?.let {
                viewModel.mutableLiveDataProduct.value = null
                replaceProductDetailsFragment(it)    //Replace ProductDetailsFragment
            }
        }
    }

    private fun setOnClickOnSliderImage() {
        sliderAdapter.setOnClickItem(object : SliderAdapterHome.OnClickOnItem{
            override fun onClick(id: String) {
                viewModel.getProductOfSliderById(id)    //Get Product Of Slider Item Image By Id
            }
        })
    }

    private fun setOnClickOnBack() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {activity!!.finish()}
            }
        activity!!.onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun replaceProductDetailsFragment(product : Product){
        //Pass Product To ProductDetailsFragment
        val args = Bundle()
        args.putSerializable(PRODUCT, product)
        val productDetailsFragment = ProductDetailsFragment()
        productDetailsFragment.arguments = args
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, productDetailsFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun observeErrorMessage() {
        viewModel.error.observe(viewLifecycleOwner) {
            it?.let {
                ToastyUtil.errorToasty(context!!, it, Toast.LENGTH_SHORT)  //Toast Error Message
                viewModel.getRoomDataBase()                                //Get Room DataBase
                viewModel.errorMessage.value = null
            }
        }
    }

    private fun setOnClickOnFoodItem() {
        foodRecyclerAdapter.setOnItemClickListener(object : ProductsHomeRecyclerAdapter.OnClickOnItem{
            override fun onClick1(product: Product) {
                replaceProductDetailsFragment(product)  //Replace ProductDetailsFragment
            }
        })
    }

    private fun setOnClickOnBeautyItem() {
        beautyRecyclerAdapter.setOnItemClickListener(object : ProductsHomeRecyclerAdapter.OnClickOnItem{
            override fun onClick1(product: Product) {
                replaceProductDetailsFragment(product)  //Replace ProductDetailsFragment
            }
        })
    }

    private fun setOnClickOnCategoriesItem() {
        categoriesRecyclerAdapter.setOnItemClickListener(object : CategoriesRecyclerAdapter.OnClickOnItem{
            override fun onClick1(cat: Category) {
                //Pass CATEGORY_ID And CATEGORY_NAME To subCategoryFragment
                val args = Bundle()
                args.putInt(CATEGORY_ID, cat.id)
                args.putString(CATEGORY_NAME, cat.name)
                val subCategoryFragment = SubCategoryFragment()
                subCategoryFragment.arguments = args
                val transaction = activity!!.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.flFragment, subCategoryFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })
    }

    private fun setOnClickOnHouseWareItem() {
        houseWareRecyclerAdapter.setOnItemClickListener(object : ProductsHomeRecyclerAdapter.OnClickOnItem{
            override fun onClick1(product: Product) {
                replaceProductDetailsFragment(product)   //Replace ProductDetailsFragment
            }
        })
    }

    private fun setOnClickOnElectronicsItem() {
        electronicsRecyclerAdapter.setOnItemClickListener(object : ProductsHomeRecyclerAdapter.OnClickOnItem{
            override fun onClick1(product: Product) {
                replaceProductDetailsFragment(product)
            }
        })
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