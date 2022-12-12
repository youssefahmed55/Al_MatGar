package com.example.e_commerce.utils

import com.example.e_commerce.pojo.Order
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.pojo.UserModel
import java.util.ArrayList

object SearchUtil {

    fun getSearchProducts(s: String, s2:List<Product>):  List<Product> {
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

    fun getSearchUsers(s: String, s2:List<UserModel>):  List<UserModel> {
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

    fun getSearchOrders(s: String, s2:List<Order>):  List<Order> {
        val arrayList: ArrayList<Order> = ArrayList<Order>()

        if (s != "")
            s2.forEach { it ->
                if (it.productName.lowercase()[0] == s.lowercase()[0])
                    if (it.productName.lowercase().contains(s.lowercase())) {
                        arrayList.add(it)
                    }
            }

        return arrayList.toList()
    }
}