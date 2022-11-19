package com.example.e_commerce.ui.homemarket.users.newuser

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.R
import com.example.e_commerce.adapters.CustomArrayAdapter
import com.example.e_commerce.databinding.BottomsheetdateBinding
import com.example.e_commerce.databinding.FragmentNewUserBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewUserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class NewUserFragment : Fragment() {
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
    private lateinit var binding : FragmentNewUserBinding
    private val viewModel: NewUSerViewModel by lazy { ViewModelProvider(this)[NewUSerViewModel::class.java] }
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var bindingBottomsheetdate : BottomsheetdateBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_new_user, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        inti(inflater,container)
        setOnclickOnBirthday()
        setSpinnersAdapter()
        setOnClickOnBackIcon()
        observeErrorMessage()
        showHidePass()
        render()

        return binding.root
    }

    private fun showHidePass(){
        binding.eyeImageNewUser.setOnClickListener {
            if(binding.passwordNewuser.transformationMethod.equals(PasswordTransformationMethod.getInstance())){
                binding.eyeImageNewUser.setImageResource(R.drawable.eye2)
                //Show Password
                binding.passwordNewuser.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            else{
                binding.eyeImageNewUser.setImageResource(R.drawable.eye1)
                //Hide Password
                binding.passwordNewuser.transformationMethod = PasswordTransformationMethod.getInstance()

            }

        }
    }

    private fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.states.collect{
                when(it){
                    is DefaultStates.Error -> Toast.makeText(requireContext(),it.error,Toast.LENGTH_SHORT).show()
                    is DefaultStates.Success -> {Toast.makeText(requireContext(),it.toastMessage,Toast.LENGTH_SHORT).show()
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
                R.string.FullName_Is_Required -> binding.fullNameNewuser.error =  getString(R.string.FullName_Is_Required)
                R.string.Email_Is_Required -> binding.emailNewuser.error =  getString(R.string.Email_Is_Required)
                R.string.Phone_Number_Is_Required -> binding.phoneNumberNewuser.error =  getString(R.string.Phone_Number_Is_Required)
                R.string.Password_Is_Required -> binding.passwordNewuser.error =  getString(R.string.Password_Is_Required)
                R.string.Gender_Is_Required -> Toast.makeText(requireContext(),getString(R.string.Gender_Is_Required),Toast.LENGTH_SHORT).show()
                R.string.Type_Is_Required -> Toast.makeText(requireContext(),getString(R.string.Type_Is_Required),Toast.LENGTH_SHORT).show()
                R.string.Birthday_Is_Required -> binding.dateOfBirthdayNewuser.error =  getString(R.string.Birthday_Is_Required)
                R.string.Location_Is_Required -> binding.addressNewuser.error =  getString(R.string.Location_Is_Required)


            }
        })
    }

    private fun setOnclickOnBirthday() {
        binding.dateOfBirthdayNewuser.setOnClickListener {

            bindingBottomsheetdate.calenderBottomSheetDate.setOnDateChangeListener { _, i, i2, i3 ->
                binding.dateOfBirthdayNewuser.text = "$i3-${i2+1}-$i"
                binding.dateOfBirthdayNewuser.error = null
                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.setContentView(bindingBottomsheetdate.root)
            bottomSheetDialog.show()
        }

    }
    private fun inti(inflater: LayoutInflater, container: ViewGroup?) {
        //Initialize bottomSheetDialog
        bottomSheetDialog = BottomSheetDialog(context!!, R.style.AppBottomSheetDialogTheme)
        bindingBottomsheetdate = DataBindingUtil.inflate(inflater,R.layout.bottomsheetdate, container, false)
    }

    private fun setOnClickOnBackIcon() {
        binding.backCardNewuser.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }
    }

    private fun setSpinnersAdapter() {
        val spinnerArrayAdapterGender = object  : CustomArrayAdapter(requireContext(),R.layout.spinner_item,resources.getStringArray(R.array.genders)){
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }
        }
        val spinnerArrayAdapterType = object  :  CustomArrayAdapter(requireContext(),R.layout.spinner_item,resources.getStringArray(R.array.types)){
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }
        }

        spinnerArrayAdapterGender.setDropDownViewResource(R.layout.spinner_item2) //Set Drop Down View Resource To Spinner
        spinnerArrayAdapterType.setDropDownViewResource(R.layout.spinner_item2)   //Set Drop Down View Resource To Spinner

        binding.genderNewuser.adapter = spinnerArrayAdapterGender
        binding.typeNewuser.adapter = spinnerArrayAdapterType


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewUserFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewUserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}