package com.example.e_commerce.adapters


import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.utils.SearchUtil


@BindingAdapter(*["bind:productAdapter", "bind:productList","bind:searchProductText","bind:productType"])
fun setListOfProducts(view: RecyclerView, productsMerchantRecyclerAdapter: ProductsMerchantRecyclerAdapter, listOfProducts : List<Product>, searchText : String, type : Int) {
    val list = if (searchText.trim().isNotEmpty()){SearchUtil.getSearchProducts(searchText,listOfProducts)} else listOfProducts
    val arrayList  = ArrayList<Product>()
    when (type){
        R.id.all_myProductsFragment -> arrayList.addAll(list)
        R.id.beauty_myProductsFragment -> list.forEach { if (it.category == "Beauty") arrayList.add(it) }
        R.id.clothes_myProductsFragment -> list.forEach { if (it.category == "Clothes") arrayList.add(it) }
        R.id.food_myProductsFragment -> list.forEach { if (it.category == "Food") arrayList.add(it) }
        R.id.houseWare_myProductsFragment -> list.forEach { if (it.category == "HouseWare") arrayList.add(it) }
    }

    productsMerchantRecyclerAdapter.setList(arrayList.toList())
    view.adapter = productsMerchantRecyclerAdapter

}

