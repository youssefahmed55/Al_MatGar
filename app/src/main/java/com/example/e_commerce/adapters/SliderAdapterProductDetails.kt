package com.example.e_commerce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ItemSliderBinding
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapterProductDetails : SliderViewAdapter<SliderAdapterProductDetails.SliderViewHolder>() {

    // on below line we are creating a
    // new array list and initializing it.
    private lateinit var onClickOnItem : OnClickOnItem
    private lateinit var listOfImages : List<String>
    interface OnClickOnItem {
        fun onClick(id : String)
    }

    fun setListOfImages(listOfImages : List<String>){
        this.listOfImages = listOfImages
    }

    // on below line we are calling get method
    override fun getCount(): Int {
        // in this method we are returning
        // the size of our slider list.
        return listOfImages.size
    }

    // on below line we are calling on create view holder method.
    override fun onCreateViewHolder(parent: ViewGroup): SliderViewHolder {
        // inside this method we are inflating our layout file for our slider view.
        val binding: ItemSliderBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_slider,parent,false)
        // on below line we are simply passing
        // the view to our slider view holder.
        return SliderViewHolder(binding)
    }

    // on below line we are calling on bind view holder method to set the data to our image view.
    override fun onBindViewHolder(sliderViewHolder: SliderViewHolder, position: Int) {
        sliderViewHolder.holderBinding.image = listOfImages[position]
    }

    // on below line we are creating a class for slider view holder.
    class SliderViewHolder(binding: ItemSliderBinding) : SliderViewAdapter.ViewHolder(binding.root) {
        // on below line we are creating a variable for our
        // image view and initializing it with image id.
        var holderBinding: ItemSliderBinding = binding
    }
}