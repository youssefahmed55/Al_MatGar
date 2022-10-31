package com.example.e_commerce.pojo

data class Order(val id : Long ,val product: Product , val date : String ,val count : Int, val status : String , val fullName : String , val phoneNumber : String , val email : String , val location : String)
