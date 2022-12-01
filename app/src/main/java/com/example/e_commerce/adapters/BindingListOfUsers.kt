package com.example.e_commerce.adapters

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.e_commerce.pojo.UserModel
import com.example.e_commerce.utils.SearchUtil

@BindingAdapter(*["bind:userAdapter", "bind:userList","bind:searchUserText","bind:UserType"])
fun setListOfUsers(view: RecyclerView, usersRecyclerAdapter: UsersRecyclerAdapter, listOfUsers : List<UserModel>, searchText : String, type : Int) {
    val list = if (searchText.trim().isNotEmpty()){SearchUtil.getSearchUsers(searchText,listOfUsers)} else listOfUsers
    val arrayList  = ArrayList<UserModel>()
    when (type){
        R.id.all_usersFragment -> arrayList.addAll(list)
        R.id.admins_usersFragment -> list.forEach { if (it.type == "Admin") arrayList.add(it) }
        R.id.customers_usersFragment -> list.forEach { if (it.type == "Customer") arrayList.add(it) }
        R.id.merchants_usersFragment -> list.forEach { if (it.type == "Merchant") arrayList.add(it) }
    }

    usersRecyclerAdapter.setList(arrayList.toList())
    view.adapter = usersRecyclerAdapter

}
