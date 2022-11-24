package com.example.e_commerce.utils

import android.net.Uri
import com.example.e_commerce.R

object NewProductUtil {

    fun checkCreateProductValid(
        nameOfProduct: String,
        description: String,
        price: String,
        hasOffer: Boolean,
        offerPrice: String,
        listOfImages: List<Uri>
    ): Int {

        if(nameOfProduct.trim().isEmpty()){return R.string.Name_Of_Product_Is_Required}
        if(description.trim().isEmpty()){return R.string.Description_Is_Required}
        if(price.trim().isEmpty()){return R.string.Price_Is_Required}
        if (price.trim().toDouble() == 0.0){
            return R.string.Price_Must_Not_To_Be_Equal_Zero
        }

        if(hasOffer){
            if (offerPrice.trim().isEmpty()){
                return R.string.Offer_Price_Is_Required
            }else{
                if (price.trim().toDouble() <= offerPrice.trim().toDouble() ){
                  return R.string.Offer_Price_Must_Be_Less_than_Price
                }
                if (offerPrice.trim().toDouble() == 0.0){
                    return R.string.Offer_Price_Must_Not_To_Be_Equal_Zero
                }
            }

        }
        if (listOfImages.size < 3 || listOfImages.size > 7){
            return R.string.You_Must_Upload_Between_Three_And_Seven_Images
        }

        return R.string.success
    }
}