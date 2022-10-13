package com.example.e_commerce.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide



@BindingAdapter("price")
fun bindTextViewToPrice(textView: TextView, number: Double) {
    textView.text = "$number $"
}

@BindingAdapter("imageUrl")
fun bindPictureToImage(image: ImageView, url: String?) {
    if(url!=null)
        Glide.with(image.context).load(url).into(image)
}
