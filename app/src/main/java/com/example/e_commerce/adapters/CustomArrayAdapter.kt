package com.example.e_commerce.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.e_commerce.R

open class CustomArrayAdapter(context: Context, resource: Int, objects: Array<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)

        if (position > 0) {
            view = LayoutInflater.from(context).inflate(R.layout.spinner_item3, parent, false)
        }

        return super.getView(position, view, parent)
    }
}