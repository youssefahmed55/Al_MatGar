package com.example.e_commerce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ItemCatagorieBinding
import com.example.e_commerce.pojo.Category


class CategoriesRecyclerAdapter : RecyclerView.Adapter<CategoriesRecyclerAdapter.Holder>() {
    private lateinit var list : List<Category>
    private lateinit var onClickOnItem : OnClickOnItem

    interface OnClickOnItem {
        fun onClick1(cat: Category)
    }

    fun setList (Listt : List<Category>){
        list = Listt
    }

    fun setOnItemClickListener (listener : OnClickOnItem){
        onClickOnItem = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding : ItemCatagorieBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_catagorie,parent,false)
        return Holder(binding,onClickOnItem,list)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.holderBinding.cat = list[position]
    }

    override fun getItemCount(): Int {
        return list.size

    }

    class Holder(binding: ItemCatagorieBinding,listener: OnClickOnItem, list: List<Category>) : RecyclerView.ViewHolder(binding.root) {
         val holderBinding : ItemCatagorieBinding = binding

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