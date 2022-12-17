package com.example.e_commerce.ui.login.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentSignUpBinding
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.utils.ToastyUtil
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


@AndroidEntryPoint
class SignUpFragment : Fragment() {
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
    private lateinit var binding : FragmentSignUpBinding
    private val viewModel: SignUpViewModel by lazy { ViewModelProvider(this)[SignUpViewModel::class.java] }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sign_up, container, false) //Initialize binding
        binding.lifecycleOwner = this  //Set lifecycleOwner
        binding.viewModel = viewModel  //Set Variable Of ViewModel (DataBinding)
        observeErrorMessage()          //Observe Error Message That Appears On EditTexts
        render()                       //render states From ViewModel

        return binding.root
    }



    private fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.states.collect{
                when(it){
                    is DefaultStates.Success -> {
                        ToastyUtil.successToasty(context!!,it.toastMessage,Toast.LENGTH_LONG) //Toast Successful Message
                        findNavController().popBackStack()    //Pop Back To Previous Fragment
                    }
                    is DefaultStates.Error -> {
                        ToastyUtil.errorToasty(context!!,it.error,Toast.LENGTH_SHORT)  //Toast Error Message
                    }

                    else -> {}
                }
            }
        }
    }

    private fun observeErrorMessage() {
        viewModel.liveDataErrorMessage.observe(viewLifecycleOwner){
            when(it){
                R.string.FullName_Is_Required -> binding.editTextFullNameSignUp.error =  getString(it)
                R.string.Email_Is_Required -> binding.editTextEmailAddressSignUp.error =  getString(it)
                R.string.Phone_Number_Is_Required -> binding.editTextPhoneNumberSignUp.error =  getString(it)
                R.string.Password_Is_Required -> binding.editTextPasswordSignUp.error =  getString(it)
                R.string.Confirm_Password_Is_Required, R.string.Password_Donot_match -> binding.editTextConfirmPasswordSignUp.error =  getString(it)
            }
        }
    }
}