package com.example.e_commerce.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ItemProduct2Binding
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.utils.Network
import com.example.e_commerce.utils.SharedPrefsUtil


class ProductsSubExploreRecyclerAdapter : RecyclerView.Adapter<ProductsSubExploreRecyclerAdapter.Holder>() {
    private lateinit var list : List<Product>
    private lateinit var onClickOnItem : OnClickOnItem
    private lateinit var onClickOnItemFavorite : OnClickOnItemFavorite
    private lateinit var context : Context

    interface OnClickOnItem {
        fun onClick1(product: Product)
    }

    interface OnClickOnItemFavorite {
        fun onClick1(id: String, isFavorite : Boolean)
    }

    fun setList (list : List<Product>){
        this.list = list
    }

    fun setContext(context : Context){
        this.context = context
    }

    fun setFavoriteItem(id : String){
        this.list.forEach { if (it.id == id) it.isFavorite = true }
    }

    fun removeFavoriteItem(id : String){
        this.list.forEach { if (it.id == id) it.isFavorite = false }
    }

    fun setOnItemClickListener (listener : OnClickOnItem){
        onClickOnItem = listener
    }

    fun setOnItemClickFavoriteListener (listener2 : OnClickOnItemFavorite){
        onClickOnItemFavorite = listener2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding : ItemProduct2Binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_product2,parent,false)
        return Holder(binding,onClickOnItem,onClickOnItemFavorite,list,context)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.holderBinding.product = list[position]
    }

    override fun getItemCount(): Int {
        return list.size

    }

    class Holder(binding: ItemProduct2Binding,listener: OnClickOnItem , listener2 : OnClickOnItemFavorite, list: List<Product>,context:Context) : RecyclerView.ViewHolder(binding.root) {
         val holderBinding : ItemProduct2Binding = binding

        init {
            binding.type = SharedPrefsUtil.getType(context)
            binding.root.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // you can trust the adapter position
                    // do whatever you intend to do with this position
                    if (listener != null)
                        listener.onClick1(list[adapterPosition])

                }
            }

            binding.favoriteCardProduct2.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // you can trust the adapter position
                    // do whatever you intend to do with this position
                    if (listener2 != null){
                        try {
                            Network.checkConnectionType(it.context)
                            listener2.onClick1(list[adapterPosition].id!!,list[adapterPosition].isFavorite!!)
                        }catch (e : Exception){
                            Toast.makeText(it.context,e.message!!,Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        }

    }
}