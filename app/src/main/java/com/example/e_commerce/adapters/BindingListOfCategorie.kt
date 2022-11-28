package com.example.e_commerce.adapters

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.pojo.Category



@BindingAdapter(*["categoriesAdapter", "categoriesList"])
fun setListOfCategories(view: RecyclerView, categoriesRecyclerAdapter: CategoriesRecyclerAdapter, listOfCategories : List<Category>) {
    view.visibility = View.VISIBLE
    categoriesRecyclerAdapter.setList(listOfCategories)
    view.adapter = categoriesRecyclerAdapter
}
