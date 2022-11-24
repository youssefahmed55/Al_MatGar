package com.example.e_commerce.adapters

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.e_commerce.pojo.Product


@BindingAdapter(*["bind:productAdapter", "bind:productList","bind:searchProductText","bind:productType"])
fun setListOfProducts(view: RecyclerView, productsMerchantRecyclerAdapter: ProductsMerchantRecyclerAdapter, listOfProducts : List<Product>, searchText : String, type : Int) {
    val list = if (searchText.trim().isNotEmpty()){getSearchProducts(searchText,listOfProducts)} else listOfProducts
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

private fun getSearchProducts(s: String, s2:List<Product>):  List<Product> {
    val arrayList: ArrayList<Product> = ArrayList<Product>()

    if (s != "")
        s2.forEach { it ->
            if (it.name?.lowercase()!![0] == s.lowercase()[0])
                if (it.name.lowercase().contains(s.lowercase())) {
                    arrayList.add(it)
                }
        }

    return arrayList.toList()
}


@BindingAdapter("selectedTypeOfProduct")
fun selectedTypeOfProduct(view: TextView, resource: Int) {
    if (view.id == resource) {
        view.setBackgroundResource(R.drawable.shape2)
        view.setTextColor(Color.WHITE)
    } else {
        view.setBackgroundResource(R.drawable.shape3)
        view.setTextColor(Color.BLACK)
    }

}