package com.example.e_commerce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ItemProduct2Binding
import com.example.e_commerce.databinding.ItemProductHomeBinding
import com.example.e_commerce.pojo.Product


class ProductsExploreRecyclerAdapter : RecyclerView.Adapter<ProductsExploreRecyclerAdapter.Holder>() {
    private lateinit var list : List<Product>
    private lateinit var onClickOnItem : OnClickOnItem

    interface OnClickOnItem {
        fun onClick1(product: Product)
    }

    fun setList (Listt : List<Product>){
        list = Listt
    }

    fun setOnItemClickListener (listener : OnClickOnItem){
        onClickOnItem = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding : ItemProduct2Binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_product2,parent,false)
        return Holder(binding,onClickOnItem,list)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.holderBinding.product = list[position]
    }

    override fun getItemCount(): Int {
        return list.size

    }

    class Holder(binding: ItemProduct2Binding,listener: OnClickOnItem, list: List<Product>) : RecyclerView.ViewHolder(binding.root) {
         val holderBinding : ItemProduct2Binding = binding

        init {
            binding.root.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // you can trust the adapter position
                    // do whatever you intend to do with this position
                    if (listener != null)
                        listener.onClick1(list[adapterPosition])

                }
            }
        }

    }
}