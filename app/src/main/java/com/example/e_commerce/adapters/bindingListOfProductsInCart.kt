package com.example.e_commerce.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.pojo.Product

@BindingAdapter("productsInCartAdapter", "productsInCartList", "CountsInCartList")
fun setListOfProductsInCart(view: RecyclerView, productsInCartRecyclerAdapter: ProductsInCartRecyclerAdapter, listOfProducts : List<Product> , listOfCounts : List<Int>) {
    if (listOfCounts.isNotEmpty()) {
        productsInCartRecyclerAdapter.setList(listOfProducts)
        productsInCartRecyclerAdapter.setListOfCount(listOfCounts)
        view.adapter = productsInCartRecyclerAdapter
    }
}