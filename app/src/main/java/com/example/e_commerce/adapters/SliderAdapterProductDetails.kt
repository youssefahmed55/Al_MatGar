package com.example.e_commerce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ItemSliderBinding
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapterProductDetails : SliderViewAdapter<SliderAdapterProductDetails.SliderViewHolder>() {

    // on below line we are creating a

    private lateinit var listOfImages : List<String>
    fun setListOfImages(listOfImages : List<String>){
        this.listOfImages = listOfImages
    }

    // on below line we are calling get method
    override fun getCount(): Int {
        // in this method we are returning
        // the size of our slider images url list.
        return listOfImages.size
    }

    // on below line we are calling on create view holder method.
    override fun onCreateViewHolder(parent: ViewGroup): SliderViewHolder {
        //Initialize binding
        val binding: ItemSliderBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_slider,parent,false)
        // on below line we are simply passing
        // the view to our slider view holder.
        return SliderViewHolder(binding)
    }

    // on below line we are calling on bind view holder method to set the data.
    override fun onBindViewHolder(sliderViewHolder: SliderViewHolder, position: Int) {
        sliderViewHolder.holderBinding.image = listOfImages[position]
    }

    // on below line we are creating a class for slider view holder.
    class SliderViewHolder(binding: ItemSliderBinding) : SliderViewAdapter.ViewHolder(binding.root) {
        // Initialize holderBinding
        var holderBinding: ItemSliderBinding = binding
    }
}