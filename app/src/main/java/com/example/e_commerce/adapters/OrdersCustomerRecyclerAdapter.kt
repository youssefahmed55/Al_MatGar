package com.example.e_commerce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ItemOrderBinding
import com.example.e_commerce.pojo.Order


class OrdersCustomerRecyclerAdapter : RecyclerView.Adapter<OrdersCustomerRecyclerAdapter.Holder>() {
    private lateinit var list : List<Order>
    private lateinit var onClickOnItem : OnClickOnItem

    interface OnClickOnItem {
        fun onClick1(productId: String)
    }

    fun setList (list : List<Order>){
        this.list = list
    }

    fun setOnItemClickListener (listener : OnClickOnItem){
        onClickOnItem = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding : ItemOrderBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_order,parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.holderBinding.order = list[position]
    }

    override fun getItemCount(): Int {
        return list.size

    }

    inner class Holder(binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
         val holderBinding : ItemOrderBinding = binding

        init {
            binding.root.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // you can trust the adapter position
                    // do whatever you intend to do with this position
                    if (onClickOnItem != null)
                        onClickOnItem.onClick1(list[adapterPosition].productId)

                }
            }
        }

    }
}