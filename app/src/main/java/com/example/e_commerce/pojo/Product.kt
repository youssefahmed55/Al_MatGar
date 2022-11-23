package com.example.e_commerce.pojo

import java.io.Serializable

data class Product(val id : String ?="", val name : String ?="", val category : String ?= "" , val PersonOrCompanyName : String ?="", val description : String ?="", val price : Double ?= 0.0, val images : List<String> ?= emptyList(), val hasOffer : Boolean ?= false, val offerPrice : Double ?= 0.0, val delivery_time : Int ?= 1) :
    Serializable
