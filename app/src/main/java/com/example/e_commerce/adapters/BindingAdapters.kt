package com.example.e_commerce.adapters


import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.R
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
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
        if(url.isNotEmpty())
        Glide.with(image.context).load(url).into(image)
}

@BindingAdapter("strikeThrough")
fun strikeThrough(textView: TextView, number: Double) {
    val format = NumberFormat.getCurrencyInstance(Locale.CANADA)
    val currency = format.format(number)
    textView.text = currency
    textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

}

@BindingAdapter("showOnLoading")
fun ProgressBar.showOnLoading(defaultStates: DefaultStates) {
    visibility = if (defaultStates is DefaultStates.Loading)
        View.VISIBLE
    else
        View.GONE
}

@BindingAdapter("showShimmerOnLoading")
fun ShimmerFrameLayout.showShimmerOnLoading(hide: Boolean) {
    visibility = if (hide) {
        stopShimmer()
        View.GONE
    }else {
        startShimmer()
        View.VISIBLE
    }
}

@BindingAdapter("canClickable")
fun canClickable(view: View, defaultStates: DefaultStates) {
    view.isClickable = defaultStates !is DefaultStates.Loading
}


@BindingAdapter("selectedType")
fun selectedType(view: TextView, resource: Int) {
    if (view.id == resource) {
        view.setBackgroundResource(R.drawable.shape2)
        view.setTextColor(Color.WHITE)
    } else {
        view.setBackgroundResource(R.drawable.shape3)
        view.setTextColor(Color.BLACK)
    }

}
@BindingAdapter("setMenu")
fun BottomNavigationView.setMenuOfBottomNavigation(res : Int){
    menu.clear()
    inflateMenu(res)
    selectedItemId = R.id.home
}


