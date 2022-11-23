package com.example.e_commerce.ui.homemarket.myproducts.addproduct

import android.content.Context
import android.net.Uri
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.R
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.utils.SharedPrefsUtil
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddProductRepo @Inject constructor(@ApplicationContext private val appContext: Context) {

   private val db  = Firebase.firestore

   suspend fun createNewProduct(nameOfProduct: String, description: String, price: String, hasOffer: Boolean, offerPrice: String , category : Int , deliveryTime :Int , listOfImages: List<Uri>) :DefaultStates = withContext(Dispatchers.IO){
       val offerPrice2 = if (!hasOffer) "0.0" else offerPrice
       val deliveryTime2 = deliveryTime + 1

       val category2 : String = when (category){
           0 -> appContext.getString(R.string.beauty)
           1 -> appContext.getString(R.string.clothes)
           2 -> appContext.getString(R.string.food)
           3 -> appContext.getString(R.string.houseware)
           else -> ""
       }

       val doc = db.collection("MyProduct").document(getIdOfUser()).collection("Product").document()
       val docId = doc.id
       val arrayListOfImages = ArrayList<String>()
       listOfImages.forEachIndexed  {index, imageUri ->
           val storageRef= FirebaseStorage.getInstance().getReference("Users").child(getIdOfUser()).child("Product").child(docId + index)
           val image = storageRef.putFile(imageUri)
               .await() // await() instead of snapshot
               .storage
               .downloadUrl
               .await() // await the url
               .toString()
           arrayListOfImages.add(image)
       }

       val product = Product(docId,nameOfProduct.trim(),category2,getNameOfUser(),description.trim(),price.trim().toDouble(),arrayListOfImages.toList(),hasOffer,offerPrice2.trim().toDouble(),deliveryTime2)

       db.collection("MyProduct").document(getIdOfUser()).collection("Product").document(docId).set(product).await()
       db.collection("AllProducts").document(docId).set(product).await()

       return@withContext DefaultStates.Success(appContext.getString(R.string.Added_Product_Successfully))
   }

    private fun getNameOfUser() : String = SharedPrefsUtil.getName(appContext)!!

    private fun getIdOfUser() : String = SharedPrefsUtil.getId(appContext)!!

}