package com.example.e_commerce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ItemIncartBinding
import com.example.e_commerce.pojo.Product


class ProductsInCartRecyclerAdapter : RecyclerView.Adapter<ProductsInCartRecyclerAdapter.Holder>() {
    private lateinit var list : List<Product>
    private lateinit var listOfCount : List<Int>
    private lateinit var onClickOnItem : OnClickOnItem
    private lateinit var onClickOnItemDelete: OnClickOnItemDelete
    private lateinit var onClickOnItemPlus: OnClickOnItemPlus
    private lateinit var onClickOnItemMinus: OnClickOnItemMinus

    interface OnClickOnItem {
        fun onClick1(product: Product)
    }

    interface OnClickOnItemDelete {
        fun onClickDelete(id : String)
    }

    interface OnClickOnItemPlus {
        fun onClickPlus(position : Int , count : Int)
    }

    interface OnClickOnItemMinus {
        fun onClickMinus(position : Int , count : Int)
    }

    fun setList (List : List<Product>){
        this.list = List
    }

    fun setListOfCount (listOfCount : List<Int>){
        this.listOfCount = listOfCount
    }

    fun getList() : List<Product>{
        return this.list
    }

    fun getListOfCount() : List<Int>{
        return this.listOfCount
    }

    fun editCount(position: Int , count: Int){
        val mutableList = mutableListOf<Int>()
        mutableList.addAll(this.listOfCount)
        mutableList[position] = count
        this.listOfCount = mutableList.toList()
    }

    fun setOnItemClickListener (listener : OnClickOnItem){
        onClickOnItem = listener
    }

    fun setOnDeleteClickListener (listener2 : OnClickOnItemDelete){
        onClickOnItemDelete = listener2
    }

    fun setOnPlusClickListener (onClickOnItemPlus: OnClickOnItemPlus){
        this.onClickOnItemPlus = onClickOnItemPlus
    }

    fun setOnMinusClickListener (onClickOnItemMinus: OnClickOnItemMinus){
        this.onClickOnItemMinus = onClickOnItemMinus
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding : ItemIncartBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_incart,parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.holderBinding.count = listOfCount[position]
        holder.holderBinding.product = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class Holder(binding: ItemIncartBinding) : RecyclerView.ViewHolder(binding.root) {
         val holderBinding : ItemIncartBinding = binding

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

            binding.deleteItemInCart.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // you can trust the adapter position
                    // do whatever you intend to do with this position
                    if (onClickOnItemDelete != null)
                        onClickOnItemDelete.onClickDelete(list[adapterPosition].id)

                }
            }

            binding.plusItemInCart.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // you can trust the adapter position
                    // do whatever you intend to do with this position
                    if (onClickOnItemPlus != null)
                        onClickOnItemPlus.onClickPlus(adapterPosition,listOfCount[adapterPosition])

                }
            }

            binding.minusItemInCart.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // you can trust the adapter position
                    // do whatever you intend to do with this position
                    if (onClickOnItemMinus != null)
                        onClickOnItemMinus.onClickMinus(adapterPosition,listOfCount[adapterPosition])

                }
            }

        }

    }
}