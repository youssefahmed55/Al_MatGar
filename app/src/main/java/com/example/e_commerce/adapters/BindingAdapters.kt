package com.example.e_commerce.adapters

import android.graphics.Paint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.NumberFormat
import java.util.*


@BindingAdapter("price")
fun bindTextViewToPrice(textView: TextView, number: Double) {
    val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale.CANADA)
    val currency: String = format.format(number)
    textView.text = currency
}

@BindingAdapter("imageUrl")
fun bindPictureToImage(image: ImageView, url: String?) {
    if(url!=null)
        Glide.with(image.context).load(url).into(image)
}

@BindingAdapter("strikeThrough")
fun strikeThrough(textView: TextView, number: Double) {
    val format = NumberFormat.getCurrencyInstance(Locale.CANADA)
    val currency = format.format(number)
    textView.text = currency
    textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

}
