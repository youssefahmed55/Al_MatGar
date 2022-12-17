package com.example.e_commerce.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.utils.SearchUtil


@BindingAdapter("productsSubExploreAdapter", "productsSubExploreList", "searchProductText")
fun RecyclerView.setListOfProductsSubExplore(productsSubExploreRecyclerAdapter: ProductsSubExploreRecyclerAdapter, listOfProducts : List<Product> , searchText : String) {

    var myList = listOfProducts
    if (searchText.trim().isNotEmpty()){
        myList = SearchUtil.getSearchProducts(searchText.trim(),myList)
    }

    productsSubExploreRecyclerAdapter.setList(myList)
    adapter = productsSubExploreRecyclerAdapter
}
