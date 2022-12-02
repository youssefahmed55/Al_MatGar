package com.example.e_commerce.adapters

import androidx.databinding.BindingAdapter
import com.smarteist.autoimageslider.SliderView


@BindingAdapter("setListOfSlider")
fun SliderView.setListOfSlider(adapter: SliderAdapter) {
    setSliderAdapter(adapter)
    startAutoCycle()
}
