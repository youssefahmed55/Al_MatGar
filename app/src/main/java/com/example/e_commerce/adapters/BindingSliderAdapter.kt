package com.example.e_commerce.adapters

import androidx.databinding.BindingAdapter
import com.example.e_commerce.pojo.SliderModel
import com.smarteist.autoimageslider.SliderView


@BindingAdapter("sliderAdapterHome", "setListOfSlidersHome")
fun SliderView.setListOfSliderHome(sliderAdapterHome: SliderAdapterHome  ,listOfSliders: List<SliderModel>) {
    sliderAdapterHome.setListOfSliders(listOfSliders)
    setSliderAdapter(sliderAdapterHome)
    startAutoCycle()
}

@BindingAdapter("sliderAdapterProductDetails", "setListOfImagesProductDetails")
fun SliderView.setListOfSliderProductDetails(sliderAdapterProductDetails: SliderAdapterProductDetails, listOfImages: List<String>) {
    sliderAdapterProductDetails.setListOfImages(listOfImages)
    setSliderAdapter(sliderAdapterProductDetails)
    startAutoCycle()
}

