package com.example.e_commerce.ui.homemarket.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.e_commerce.R
import com.example.e_commerce.databinding.Bottomsheet1Binding
import com.example.e_commerce.databinding.BottomsheetgenderBinding
import com.example.e_commerce.databinding.FragmentAccountBinding
import com.example.e_commerce.utils.SharedPrefsUtil
import com.google.android.material.bottomsheet.BottomSheetDialog

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment() {
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
    private lateinit var binding : FragmentAccountBinding
    private lateinit var bindingBottomSheetGender : BottomsheetgenderBinding
    private lateinit var bottomSheetDialog: BottomSheetDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_account, container, false)
        binding.lifecycleOwner = this
        inti(inflater,container)
        val userModel = SharedPrefsUtil.getUserModel(context!!)
        binding.userModel = userModel

        setOnclickOnGender()

        return  binding.root
    }

    private fun setOnclickOnGender() {
        binding.genderLinearAccountFragment.setOnClickListener {
            bindingBottomSheetGender.selected = binding.genderAccountFragment.text.toString()
            bindingBottomSheetGender.radioGroupBottomSheetGender.setOnCheckedChangeListener { _, id ->
                binding.genderAccountFragment.text = when(id){
                    R.id.radioButtonMale_bottomSheetGender -> getString(R.string.male)
                    R.id.radioButtonFemale_bottomSheetGender -> getString(R.string.female)
                    else -> ""
                }

            }
            bottomSheetDialog.setContentView(bindingBottomSheetGender.root)
            bottomSheetDialog.show()

        }

    }

    private fun inti(inflater: LayoutInflater, container: ViewGroup?) {
        //Initialize bottomSheetDialog
        bottomSheetDialog = BottomSheetDialog(context!!, R.style.AppBottomSheetDialogTheme)
        bindingBottomSheetGender = DataBindingUtil.inflate(inflater,R.layout.bottomsheetgender, container, false)
    }

    /*private fun onClickOnAnyLinear() {
        binding.emailLinearAccountFragment.setOnClickListener {
            bindingBottomSheet.edittextBottomSheet1.hint = "Email"
            bindingBottomSheet.saveButtonBottomSheet1.setOnClickListener {
                binding.emailAccountFragment.text = bindingBottomSheet.edittextBottomSheet1.text.toString()
            }
            bottomSheetDialog.setContentView(bindingBottomSheet.root)
            bottomSheetDialog.show()

        }
    }*/


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}