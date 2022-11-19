package com.example.e_commerce.adapters

import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.R
import com.example.e_commerce.pojo.UserModel
import java.text.NumberFormat
import java.util.*


@BindingAdapter("price")
fun bindTextViewToPrice(textView: TextView, number: Double) {
    val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale.CANADA)
    val currency: String = format.format(number)
    textView.text = currency
}

@BindingAdapter("imageUrl")
fun bindPictureToImage(image: ImageView, url: String?) {
    if(url!=null)
        if(url.isNotEmpty())
        Glide.with(image.context).load(url).into(image)
}

@BindingAdapter("strikeThrough")
fun strikeThrough(textView: TextView, number: Double) {
    val format = NumberFormat.getCurrencyInstance(Locale.CANADA)
    val currency = format.format(number)
    textView.text = currency
    textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

}

@BindingAdapter("showOnLoading")
fun ProgressBar.showOnLoading(defaultStates: DefaultStates) {
    visibility = if (defaultStates is DefaultStates.Loading)
        View.VISIBLE
    else
        View.GONE
}

@BindingAdapter("canClickable")
fun canClickable(view: View, defaultStates: DefaultStates) {
    view.isClickable = defaultStates !is DefaultStates.Loading
}

@BindingAdapter(*["bind:usersAdapter", "bind:usersList","bind:searchText"])
fun setListOfUsers(view: RecyclerView, usersRecyclerAdapter: UsersRecyclerAdapter , listOfUsers : List<UserModel>, searchText : String) {
            val list = if (searchText.trim().isNotEmpty()){getSearchUsers(searchText,listOfUsers)} else listOfUsers

            usersRecyclerAdapter.setList(list)
            view.adapter = usersRecyclerAdapter

}

private fun getSearchUsers(s: String, s2:List<UserModel>):  List<UserModel> {
    val arrayList: ArrayList<UserModel> = ArrayList<UserModel>()

    if (s != "")
        s2.forEach { it ->
            if (it.fullName?.lowercase()!![0] == s.lowercase()[0])
                if (it.fullName.lowercase().contains(s.lowercase())) {
                    arrayList.add(it)
                }
        }

    return arrayList.toList()
}


@BindingAdapter("selectedType")
fun selectedType(view: TextView, resource: Int) {


        if (view.id == resource) {
            view.setBackgroundResource(R.drawable.shape2)
            view.setTextColor(Color.WHITE)
        } else {
            view.setBackgroundResource(R.drawable.shape3)
            view.setTextColor(Color.BLACK)
                  }

}

/*@BindingAdapter(value = ["selectedValue", "selectedValueAttrChanged"], requireAll = false)
fun bindSpinnerData(pAppCompatSpinner: AppCompatSpinner, newSelectedValue: String?, newTextAttrChanged: InverseBindingListener) {
    pAppCompatSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
            newTextAttrChanged.onChange()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
    if (newSelectedValue != null) {
        val pos = (pAppCompatSpinner.adapter as ArrayAdapter<String?>).getPosition(newSelectedValue)
        pAppCompatSpinner.setSelection(pos, true)
    }
}

@InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
fun captureSelectedValue(pAppCompatSpinner: AppCompatSpinner): String {
    return pAppCompatSpinner.selectedItem as String
}*/
