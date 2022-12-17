package com.example.e_commerce.ui.homemarket.myproducts.addproduct

import android.content.Context
import android.net.Uri
import com.example.e_commerce.Constants.BEAUTY
import com.example.e_commerce.Constants.ELECTRONICS
import com.example.e_commerce.Constants.FOOD
import com.example.e_commerce.Constants.HOUSE_WARE
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.R
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.utils.Network
import com.example.e_commerce.utils.SharedPrefsUtil
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class AddProductRepo @Inject constructor(@ApplicationContext private val appContext: Context, private val db : FirebaseFirestore) {
   //Create New Product
   suspend fun createNewProduct(nameOfProduct: String, description: String, price: String, hasOffer: Boolean, offerPrice: String , category : Int , deliveryTime :Int , listOfImages: List<Uri>) :DefaultStates = withContext(Dispatchers.IO){
       Network.checkConnectionType(appContext)
       val offerPrice2 = if (!hasOffer) "0.0" else offerPrice
       val deliveryTime2 = deliveryTime + 1

       val category2 : String = when (category){
           0 -> BEAUTY
           1 -> ELECTRONICS
           2 -> FOOD
           3 -> HOUSE_WARE
           else -> ""
       }

       val doc = db.collection("MyProduct").document(getIdOfUser()).collection("Product").document()
       val docId = doc.id
       val myRandomValue = Random.nextInt(0, 2)
       val product = Product(docId,nameOfProduct.trim(),category2,SharedPrefsUtil.getName(appContext)!!,getIdOfUser(),description.trim(),price.trim().toDouble(), null,hasOffer,offerPrice2.trim().toDouble(),deliveryTime2,myRandomValue)

       db.collection("MyProduct").document(getIdOfUser()).collection("Products").document(docId).set({null}).await()
       db.collection("AllProducts").document(docId).set(product).await()


       val mutableListOfImages = mutableListOf<String>()
       listOfImages.forEachIndexed  {index, imageUri ->
           //Add Images To Firebase Storage
           val image = FirebaseStorage.getInstance().getReference("Users").child(getIdOfUser()).child("Products").child(docId).child(docId + index).putFile(imageUri)
               .await() // await() instead of snapshot
               .storage
               .downloadUrl
               .await() // await the url
               .toString()

           mutableListOfImages.add(image)

           db.collection("AllProducts").document(docId).update("images",mutableListOfImages).await()
       }



       return@withContext DefaultStates.Success(appContext.getString(R.string.Added_Product_Successfully))
   }



    private fun getIdOfUser() : String = SharedPrefsUtil.getId(appContext)!!

}