package com.example.e_commerce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ItemSliderBinding
import com.example.e_commerce.pojo.SliderModel
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapterHome : SliderViewAdapter<SliderAdapterHome.SliderViewHolder>() {

    // on below line we are creating a
    private lateinit var onClickOnItem : OnClickOnItem
    private lateinit var listOfSliders : List<SliderModel>
    interface OnClickOnItem {
        fun onClick(id : String)
    }

    fun setListOfSliders(listOfSliders : List<SliderModel>){
        this.listOfSliders = listOfSliders
    }

    fun setOnClickItem(onClickOnItem : OnClickOnItem){
        this.onClickOnItem = onClickOnItem
    }

    // on below line we are calling get method
    override fun getCount(): Int {
        // in this method we are returning
        // the size of our slider list.
        return listOfSliders.size
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
        sliderViewHolder.holderBinding.image = listOfSliders[position].image
        sliderViewHolder.holderBinding.root.setOnClickListener {
            if (position != RecyclerView.NO_POSITION) {
                // you can trust the adapter position
                // do whatever you intend to do with this position
                if (onClickOnItem != null){
                    onClickOnItem.onClick(listOfSliders[position].productId)
                }

            }
        }
    }

    // on below line we are creating a class for slider view holder.
    class SliderViewHolder(binding: ItemSliderBinding) : SliderViewAdapter.ViewHolder(binding.root) {
        // Initialize holderBinding
        var holderBinding: ItemSliderBinding = binding
    }
}