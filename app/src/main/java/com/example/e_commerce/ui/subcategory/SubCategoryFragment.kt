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

        val listOfProducts = listOf<Product>(Product(55,"MJ Hair USA Leave In Conditioner with keratin and protein,Anti Frizz (500 ml)","MJ HAIR USA\n" + "Hair type\tAll\n" + "Liquid volume\t16.9 Fluid Ounces","This is an International Product from the US and may differ from local products, including labelling language and information, allergen disclosures, ingredients and instructions. Read the product information carefully to determine if this product is appropriate for you. Actual product packaging and materials may contain more and different information than what is shown on our website. We recommend that you do not rely solely on the information presented and that you always read labels, warnings, and directions before using or consuming a product.",55.00, listOf("https://cdn11.bigcommerce.com/s-rxzabllq/images/stencil/1280x1280/products/293/20782/Green-Stedman-Classic-Cotton-Blank-Tshirt-Junior-Kids__43974.1579611697.jpg","https://explosiontshirt.com/wp-content/uploads/2018/10/men-irish-green-tshirt-gildan-adult-front1.jpg","https://i5.walmartimages.com/asr/ac8f818d-2e8c-4bdd-b432-73692dd3d4c3.475e7e04faa39a9bc6e5f2d1c4cea2d2.jpeg"),true,44.0,1)
            ,Product(70,"Food","Food","dddd",60.00, listOf("https://cdn11.bigcommerce.com/s-rxzabllq/images/stencil/1280x1280/products/293/20782/Green-Stedman-Classic-Cotton-Blank-Tshirt-Junior-Kids__43974.1579611697.jpg","https://explosiontshirt.com/wp-content/uploads/2018/10/men-irish-green-tshirt-gildan-adult-front1.jpg","https://i5.walmartimages.com/asr/ac8f818d-2e8c-4bdd-b432-73692dd3d4c3.475e7e04faa39a9bc6e5f2d1c4cea2d2.jpeg"),true,44.0,1)
            ,Product(80,"Food","Food","dddd",70.00, listOf("https://cdn11.bigcommerce.com/s-rxzabllq/images/stencil/1280x1280/products/293/20782/Green-Stedman-Classic-Cotton-Blank-Tshirt-Junior-Kids__43974.1579611697.jpg","https://explosiontshirt.com/wp-content/uploads/2018/10/men-irish-green-tshirt-gildan-adult-front1.jpg","https://i5.walmartimages.com/asr/ac8f818d-2e8c-4bdd-b432-73692dd3d4c3.475e7e04faa39a9bc6e5f2d1c4cea2d2.jpeg"),true,44.0,1)
            ,Product(90,"Food","Food","dddd",80.00, listOf("https://cdn11.bigcommerce.com/s-rxzabllq/images/stencil/1280x1280/products/293/20782/Green-Stedman-Classic-Cotton-Blank-Tshirt-Junior-Kids__43974.1579611697.jpg","https://explosiontshirt.com/wp-content/uploads/2018/10/men-irish-green-tshirt-gildan-adult-front1.jpg","https://i5.walmartimages.com/asr/ac8f818d-2e8c-4bdd-b432-73692dd3d4c3.475e7e04faa39a9bc6e5f2d1c4cea2d2.jpeg"),true,44.0,1)
            ,Product(100,"Food","Food","dddd",55.00, listOf("https://cdn11.bigcommerce.com/s-rxzabllq/images/stencil/1280x1280/products/293/20782/Green-Stedman-Classic-Cotton-Blank-Tshirt-Junior-Kids__43974.1579611697.jpg","https://explosiontshirt.com/wp-content/uploads/2018/10/men-irish-green-tshirt-gildan-adult-front1.jpg","https://i5.walmartimages.com/asr/ac8f818d-2e8c-4bdd-b432-73692dd3d4c3.475e7e04faa39a9bc6e5f2d1c4cea2d2.jpeg"),true,44.0,1))


        productsRecyclerAdapter.setOnItemClickListener(object : ProductsExploreRecyclerAdapter.OnClickOnItem{
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
        setProductRecyclerAdapter(listOfProducts)

        return binding.root
    }

    private fun setProductRecyclerAdapter(listOfProducts: List<Product>) {
        productsRecyclerAdapter.setList(listOfProducts)
        binding.recyclerSubCategoryFragment.adapter = productsRecyclerAdapter
    }

    private fun setOnClickOnBackIcon() {
      binding.backCardSubCategoryFragment.setOnClickListener {
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