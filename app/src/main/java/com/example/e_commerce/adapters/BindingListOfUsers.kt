package com.example.e_commerce.adapters

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.e_commerce.pojo.UserModel

@BindingAdapter(*["bind:userAdapter", "bind:userList","bind:searchUserText","bind:UserType"])
fun setListOfUsers(view: RecyclerView, usersRecyclerAdapter: UsersRecyclerAdapter, listOfUsers : List<UserModel>, searchText : String, type : Int) {
    val list = if (searchText.trim().isNotEmpty()){getSearchUsers(searchText,listOfUsers)} else listOfUsers
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

private fun getSearchUsers(s: String, s2:List<UserModel>):  List<UserModel> {
    val arrayList: ArrayList<UserModel> = ArrayList<UserModel>()

    if (s != "")
        s2.forEach { it ->
            if (it.fullName?.lowercase()!![0] == s.lowercase()[0])
                if (it.fullName.lowercase().contains(s.lowercase())) {
                    arrayList.add(it)
                }
        }

    return arrayList.toList()
}


@BindingAdapter("selectedTypeOfUsers")
fun selectedTypeOfUsers(view: TextView, resource: Int) {


    if (view.id == resource) {
        view.setBackgroundResource(R.drawable.shape2)
        view.setTextColor(Color.WHITE)
    } else {
        view.setBackgroundResource(R.drawable.shape3)
        view.setTextColor(Color.BLACK)
    }

}