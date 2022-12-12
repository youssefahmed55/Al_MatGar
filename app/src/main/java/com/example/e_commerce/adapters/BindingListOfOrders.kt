package com.example.e_commerce.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.pojo.Order
import com.example.e_commerce.utils.SearchUtil

@BindingAdapter(*["ordersMerchantAdapter", "ordersMerchantList","searchMerchantOrdersText"])
fun RecyclerView.setListOfOrdersMerchant(ordersMerchantRecyclerAdapter: OrdersMerchantRecyclerAdapter, listOfOrders : List<Order>, searchText : String) {

    var myList = listOfOrders
    if (searchText.trim().isNotEmpty()){
        myList = SearchUtil.getSearchOrders(searchText.trim(),myList)
    }

    ordersMerchantRecyclerAdapter.setList(myList)
    adapter = ordersMerchantRecyclerAdapter
}