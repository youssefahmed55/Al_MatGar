package com.example.e_commerce.adapters


import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.Constants.BEAUTY
import com.example.e_commerce.Constants.ELECTRONICS
import com.example.e_commerce.Constants.FOOD
import com.example.e_commerce.Constants.HOUSE_WARE
import com.example.e_commerce.R
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.utils.SearchUtil


@BindingAdapter("productAdapterMerchant", "productListMerchant", "searchProductTextMerchant", "productTypeMerchant")
fun setListOfProductsMerchant(view: RecyclerView, productsMerchantRecyclerAdapter: ProductsMerchantRecyclerAdapter, listOfProducts : List<Product>, searchText : String, type : Int) {
    val list = if (searchText.trim().isNotEmpty()){SearchUtil.getSearchProducts(searchText,listOfProducts)} else listOfProducts
    val arrayList  = ArrayList<Product>()
    when (type){
        R.id.all_myProductsFragment -> arrayList.addAll(list)
        R.id.beauty_myProductsFragment -> list.forEach { if (it.category == BEAUTY) arrayList.add(it) }
        R.id.Electronics_myProductsFragment -> list.forEach { if (it.category == ELECTRONICS) arrayList.add(it) }
        R.id.food_myProductsFragment -> list.forEach { if (it.category == FOOD) arrayList.add(it) }
        R.id.houseWare_myProductsFragment -> list.forEach { if (it.category == HOUSE_WARE) arrayList.add(it) }
    }

    productsMerchantRecyclerAdapter.setList(arrayList.toList())
    view.adapter = productsMerchantRecyclerAdapter

}
