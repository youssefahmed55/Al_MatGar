package com.example.e_commerce.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.pojo.Order

@BindingAdapter(*["orderCustomerAdapter", "ordersCustomerList"])
fun setListOfProductsInCart(view: RecyclerView, ordersCustomerRecyclerAdapter: OrdersCustomerRecyclerAdapter, listOfOrders : List<Order>) {
    ordersCustomerRecyclerAdapter.setList(listOfOrders)
    view.adapter = ordersCustomerRecyclerAdapter
}