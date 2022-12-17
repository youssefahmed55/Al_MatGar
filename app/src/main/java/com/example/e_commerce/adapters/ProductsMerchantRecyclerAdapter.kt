package com.example.e_commerce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ItemProductMerchantBinding
import com.example.e_commerce.pojo.Product


class ProductsMerchantRecyclerAdapter : RecyclerView.Adapter<ProductsMerchantRecyclerAdapter.Holder>() {
    private lateinit var list : List<Product>
    private lateinit var onClickOnItem : OnClickOnItem
    private lateinit var onClickOnItemDelete: OnClickOnItemDelete

    interface OnClickOnItem {
        fun onClick1(product: Product)
    }

    interface OnClickOnItemDelete {
        fun onClickDelete(id : String , list : List<String>?)
    }

    fun setList (List : List<Product>){
        this.list = List.reversed()
    }

    fun setOnItemClickListener (listener : OnClickOnItem){
        onClickOnItem = listener
    }

    fun setOnDeleteClickListener (listener2 : OnClickOnItemDelete){
        onClickOnItemDelete = listener2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding : ItemProductMerchantBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_product_merchant,parent,false) //Initialize binding
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.holderBinding.product = list[position]
    }

    override fun getItemCount(): Int {
        return list.size

    }

   inner class Holder(binding: ItemProductMerchantBinding) : RecyclerView.ViewHolder(binding.root) {
         val holderBinding : ItemProductMerchantBinding = binding

        init {
            binding.root.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // you can trust the adapter position
                    // do whatever you intend to do with this position
                    if (onClickOnItem != null)
                        onClickOnItem.onClick1(list[adapterPosition])

                }
            }

            binding.deleteItemProductMerchant.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // you can trust the adapter position
                    // do whatever you intend to do with this position
                    if (onClickOnItemDelete != null)
                        onClickOnItemDelete.onClickDelete(list[adapterPosition].id,list[adapterPosition].images)

                }
            }
        }

    }
}