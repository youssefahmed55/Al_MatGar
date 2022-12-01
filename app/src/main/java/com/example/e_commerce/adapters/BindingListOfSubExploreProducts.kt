package com.example.e_commerce.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.pojo.Product
import com.example.e_commerce.utils.SearchUtil


@BindingAdapter(*["productsSubExploreAdapter", "productsSubExploreList","favoriteSubExploreList","searchProductText"])
fun RecyclerView.setListOfProductsSubExplore(productsSubExploreRecyclerAdapter: ProductsSubExploreRecyclerAdapter, listOfProducts : List<Product> , listOfFavorites : List<String> , searchText : String) {

    listOfFavorites.forEach { favorite ->
        listOfProducts.forEach { product ->
            if (favorite == product.id){product.isFavorite = true}
        }
    }

    var myList = listOfProducts
    if (searchText.trim().isNotEmpty()){
        myList = SearchUtil.getSearchProducts(searchText.trim(),myList)
    }

    productsSubExploreRecyclerAdapter.setList(myList)
    adapter = productsSubExploreRecyclerAdapter
}
