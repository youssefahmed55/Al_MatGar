package com.example.e_commerce.adapters

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.pojo.Product


@BindingAdapter(*["productsHomeAdapter", "productsHomeList"])
fun setListOfProductsHome(view: RecyclerView, productsHomeRecyclerAdapter: ProductsHomeRecyclerAdapter, listOfProductsHome : List<Product>) {
    view.visibility = View.VISIBLE
    productsHomeRecyclerAdapter.setList(listOfProductsHome)
    view.adapter = productsHomeRecyclerAdapter
}
