package com.example.e_commerce.ui.homemarket.myproducts.addproduct

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.e_commerce.Constants.PICK_IMAGE_MULTIPLE
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentAddProductBinding
import com.example.e_commerce.utils.ToastyUtil
import dagger.hilt.android.AndroidEntryPoint


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class AddProductFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private val viewModel: AddProductViewModel by lazy { ViewModelProvider(this)[AddProductViewModel::class.java] }
    private lateinit var binding : FragmentAddProductBinding
    private val listOfUri : MutableList<Uri>  = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_add_product, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        observeErrorMessage()
        render()
        setOnClickOnBackIcon()
        setOnClickOnBack()
        setOnClickOnUploadImages()
        setOnClickOnSaveButton()

        return binding.root
    }

    private fun setOnClickOnBack() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {}
            }
        activity!!.onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun setOnClickOnUploadImages() {
        binding.uploadImageAddProduct.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_MULTIPLE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // When an Image is picked
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
            if (data.clipData != null) {
                listOfUri.clear()
                val count = data.clipData?.itemCount
                for (i in 0 until count!!) {
                    listOfUri.add(data.clipData?.getItemAt(i)?.uri!!)
                }
                binding.uploadedImagesCountAddProduct.text =  count.toString() + " " + getString(R.string.Photos_Uploaded)
            }


        }
    }

    private fun setOnClickOnSaveButton() {
        binding.saveButtonAddProduct.setOnClickListener {
            viewModel.saveNewProduct(listOfUri.toList())
        }
    }

    private fun setOnClickOnBackIcon() {
        binding.backCardAddProduct.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }
    }

    private fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.states.collect{
                when (it) {
                    is DefaultStates.Error -> ToastyUtil.errorToasty(context!!,it.error,Toast.LENGTH_SHORT)
                    is DefaultStates.Success -> {
                        ToastyUtil.successToasty(context!!,it.toastMessage,Toast.LENGTH_SHORT)
                        activity!!.supportFragmentManager.popBackStack()
                    }
                    else -> {}
                }
            }

        }
    }

    private fun observeErrorMessage() {
        viewModel.liveDataErrorMessage.observe(viewLifecycleOwner, Observer {
            when(it){
                R.string.Name_Of_Product_Is_Required -> binding.nameOfProductAddProduct.error = getString(it)
                R.string.Description_Is_Required -> binding.descriptionAddProduct.error = getString(it)
                R.string.Price_Is_Required , R.string.Price_Must_Not_To_Be_Equal_Zero ->   binding.priceOfProductAddProduct.error = getString(it)
                R.string.Offer_Price_Must_Be_Less_than_Price , R.string.Offer_Price_Is_Required , R.string.Offer_Price_Must_Not_To_Be_Equal_Zero -> binding.offerPriceOfProductAddProduct.error = getString(it)
                R.string.You_Must_Upload_Between_Three_And_Seven_Images -> ToastyUtil.errorToasty(context!!,getString(it),Toast.LENGTH_SHORT)
                else -> {}

            }
        })
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddProductFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}