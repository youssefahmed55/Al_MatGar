package com.example.e_commerce.ui.login.forgetpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentForgetPasswordBinding
import com.example.e_commerce.utils.ToastyUtil

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ForgetPasswordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForgetPasswordFragment : Fragment() {
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
    private lateinit var binding : FragmentForgetPasswordBinding
    private val viewModel: ForgetPasswordViewModel by lazy { ViewModelProvider(this)[ForgetPasswordViewModel::class.java] }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_forget_password, container, false) //Initialize binding
        binding.lifecycleOwner = this //Set lifecycleOwner
        binding.viewModel = viewModel //Set Variable Of ViewModel (DataBinding)

        observeErrorMessage()  //Observe Error Message That Appears On EditText
        observeErrorToast()    //Observe Error
        observeSentPassword()  //Observe Sent Password Result

        return binding.root
    }

    private fun observeSentPassword() {
        viewModel.liveDataSentPassword.observe(viewLifecycleOwner){
            ToastyUtil.infoToasty(context!!,getString(R.string.please_Check_your_email_address),Toast.LENGTH_LONG) //Toast Info
            findNavController().popBackStack() //Pop Back To Previous Fragment
        }
    }

    private fun observeErrorToast() {
        viewModel.liveDataErrorToast.observe(viewLifecycleOwner){
            ToastyUtil.errorToasty(context!!,it,Toast.LENGTH_SHORT) //Toast Error Message
        }
    }

    private fun observeErrorMessage() {
        viewModel.liveDataErrorMessage.observe(viewLifecycleOwner){
        binding.editTextEmailForgetPassFragment.error = it
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ForgetPasswordFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ForgetPasswordFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}