package com.example.e_commerce.pojo

import java.io.Serializable

data class Product(val id : Int , val name : String ,val PersonOrCompanyName : String, val description : String , val price : Double , val images : List<String>,val hasOffer : Boolean , val offerPrice : Double , val delivery_time : Int) :
    Serializable
