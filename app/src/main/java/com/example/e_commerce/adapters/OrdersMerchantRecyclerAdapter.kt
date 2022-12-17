package com.example.e_commerce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ItemOrderMerchantBinding
import com.example.e_commerce.pojo.Order


class OrdersMerchantRecyclerAdapter : RecyclerView.Adapter<OrdersMerchantRecyclerAdapter.Holder>() {
    private lateinit var list : List<Order>
    private lateinit var onClickOnItem : OnClickOnItem
    private lateinit var onClickOnCancel : OnClickOnCancel
    private lateinit var onClickOnDone : OnClickOnDone

    interface OnClickOnItem {
        fun onClick1(productId: String)
    }

    interface OnClickOnCancel {
        fun onClick1(orderId: String, customerId : String)
    }

    interface OnClickOnDone {
        fun onClick1(orderId: String, customerId : String)
    }

    fun setList (list : List<Order>){
        this.list = list.reversed()
    }

    fun setOnItemClickListener (onClickOnItem : OnClickOnItem){
        this.onClickOnItem = onClickOnItem
    }
    fun setOnCancelClickListener (onClickOnCancel : OnClickOnCancel){
        this.onClickOnCancel = onClickOnCancel
    }
    fun setOnDoneClickListener (onClickOnDone : OnClickOnDone){
        this.onClickOnDone = onClickOnDone
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding : ItemOrderMerchantBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_order_merchant,parent,false) //Initialize binding
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.holderBinding.order = list[position]
    }

    override fun getItemCount(): Int {
        return list.size

    }

    inner class Holder(binding: ItemOrderMerchantBinding) : RecyclerView.ViewHolder(binding.root) {
         val holderBinding : ItemOrderMerchantBinding = binding

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

            binding.cancelButtonItemOrderMerchant.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // you can trust the adapter position
                    // do whatever you intend to do with this position
                    if (onClickOnCancel != null)
                        onClickOnCancel.onClick1(list[adapterPosition].id,list[adapterPosition].customerId)
                }
            }

            binding.doneButtonItemOrderMerchant.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // you can trust the adapter position
                    // do whatever you intend to do with this position
                    if (onClickOnDone != null)
                        onClickOnDone.onClick1(list[adapterPosition].id,list[adapterPosition].customerId)
                }
            }
        }

    }
}