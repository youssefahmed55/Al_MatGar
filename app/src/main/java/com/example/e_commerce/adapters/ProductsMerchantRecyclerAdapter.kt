package com.example.e_commerce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ItemProductMerchantBinding
import com.example.e_commerce.databinding.ItemUserBinding
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.pojo.UserModel


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
        this.list = List
    }

    fun setOnItemClickListener (listener : OnClickOnItem){
        onClickOnItem = listener
    }

    fun setOnDeleteClickListener (listener2 : OnClickOnItemDelete){
        onClickOnItemDelete = listener2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding : ItemProductMerchantBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_product_merchant,parent,false)
        return Holder(binding,onClickOnItem,onClickOnItemDelete,list)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.holderBinding.product = list[position]
    }

    override fun getItemCount(): Int {
        return list.size

    }

    class Holder(binding: ItemProductMerchantBinding,listener: OnClickOnItem , listener2 : OnClickOnItemDelete , list: List<Product>) : RecyclerView.ViewHolder(binding.root) {
         val holderBinding : ItemProductMerchantBinding = binding

        init {
            binding.imageCardItemInCart.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // you can trust the adapter position
                    // do whatever you intend to do with this position
                    if (listener != null)
                        listener.onClick1(list[adapterPosition])

                }
            }

            binding.deleteItemInCart.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // you can trust the adapter position
                    // do whatever you intend to do with this position
                    if (listener2 != null)
                        listener2.onClickDelete(list[adapterPosition].id!!,list[adapterPosition].images)

                }
            }
        }

    }
}