package com.example.e_commerce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ItemUserBinding
import com.example.e_commerce.pojo.UserModel


class UsersRecyclerAdapter : RecyclerView.Adapter<UsersRecyclerAdapter.Holder>() {
    private lateinit var list : List<UserModel>
    private lateinit var onClickOnItem : OnClickOnItem

    interface OnClickOnItem {
        fun onClick1(userModel: UserModel)
    }

    fun setList (List : List<UserModel>){
        this.list = List
    }

    fun setOnItemClickListener (listener : OnClickOnItem){
        onClickOnItem = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding : ItemUserBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_user,parent,false)
        return Holder(binding,onClickOnItem,list)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.holderBinding.user = list[position]
    }

    override fun getItemCount(): Int {
        return list.size

    }

    class Holder(binding: ItemUserBinding,listener: OnClickOnItem, list: List<UserModel>) : RecyclerView.ViewHolder(binding.root) {
         val holderBinding : ItemUserBinding = binding

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