package com.example.e_commerce.ui.homemarket.users.newuser

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.R
import com.example.e_commerce.adapters.CustomArrayAdapter
import com.example.e_commerce.databinding.BottomsheetdateBinding
import com.example.e_commerce.databinding.FragmentNewUserBinding
import com.example.e_commerce.utils.ToastyUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


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
    private lateinit var bindingBottomSheetDate : BottomsheetdateBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity!!.findViewById<RelativeLayout>(R.id.relative1_homeActivity).visibility = View.GONE //Set Activity's RelativeLayout GONE
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_new_user, container, false) //Initialize binding
        binding.lifecycleOwner = this //Set lifecycleOwner
        binding.viewModel = viewModel //Set Variable Of ViewModel (DataBinding)
        inti(inflater,container)      //Initialize Variables
        setOnClickOnBirthday()        //Set On Click On Birthday
        setSpinnersAdapter()          //Set Spinners Adapter
        setOnClickOnBackIcon()        //Set On Click On Back Icon
        observeErrorMessage()         //Observe Error Message That Appears On EditTexts
        showHidePass()                //Show And Hide Password
        render()                      //render states From ViewModel

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
                    is DefaultStates.Error -> ToastyUtil.errorToasty(context!!,it.error,Toast.LENGTH_SHORT) //Toast Error Message
                    is DefaultStates.Success -> {ToastyUtil.successToasty(context!!,it.toastMessage,Toast.LENGTH_SHORT) //Toast Successful Message
                        activity!!.supportFragmentManager.popBackStack() //Pop Back To Previous Fragment
                    }
                    else -> {}
                }

            }

        }
    }

    private fun observeErrorMessage() {
        viewModel.liveDataErrorMessage.observe(viewLifecycleOwner) {
            when(it){
                R.string.FullName_Is_Required -> binding.fullNameNewuser.error =  getString(it)
                R.string.Email_Is_Required -> binding.emailNewuser.error =  getString(it)
                R.string.Phone_Number_Is_Required -> binding.phoneNumberNewuser.error =  getString(it)
                R.string.Password_Is_Required -> binding.passwordNewuser.error =  getString(it)
                R.string.Gender_Is_Required -> ToastyUtil.errorToasty(context!!,getString(it),Toast.LENGTH_SHORT) //Toast Error Message
                R.string.Type_Is_Required -> ToastyUtil.errorToasty(context!!,getString(it),Toast.LENGTH_SHORT)   //Toast Error Message
                R.string.Birthday_Is_Required -> binding.dateOfBirthdayNewuser.error =  getString(it)
                R.string.Location_Is_Required -> binding.addressNewuser.error =  getString(it)
            }
        }
    }

    private fun setOnClickOnBirthday() {
        binding.dateOfBirthdayNewuser.setOnClickListener {

            bindingBottomSheetDate.calenderBottomSheetDate.setOnDateChangeListener { _, i, i2, i3 ->
                binding.dateOfBirthdayNewuser.text = "$i3-${i2+1}-$i"
                binding.dateOfBirthdayNewuser.error = null
                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.setContentView(bindingBottomSheetDate.root)
            bottomSheetDialog.show()
        }

    }
    private fun inti(inflater: LayoutInflater, container: ViewGroup?) {
        //Initialize bottomSheetDialog
        bottomSheetDialog = BottomSheetDialog(context!!, R.style.AppBottomSheetDialogTheme)
        //Initialize bindingBottomSheetDate
        bindingBottomSheetDate = DataBindingUtil.inflate(inflater,R.layout.bottomsheetdate, container, false)
    }

    private fun setOnClickOnBackIcon() {
        binding.backCardNewuser.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack() //Pop Back To Previous Fragment
        }
    }

    private fun setSpinnersAdapter() {
        //Initialize spinnerArrayAdapterGender
        val spinnerArrayAdapterGender = object  : CustomArrayAdapter(context!!,R.layout.spinner_item,resources.getStringArray(R.array.genders)){
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }
        }
        //Initialize spinnerArrayAdapterType
        val spinnerArrayAdapterType = object  :  CustomArrayAdapter(context!!,R.layout.spinner_item,resources.getStringArray(R.array.types)){
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }
        }

        spinnerArrayAdapterGender.setDropDownViewResource(R.layout.spinner_item2) //Set Drop Down View Resource To Spinner
        spinnerArrayAdapterType.setDropDownViewResource(R.layout.spinner_item2)   //Set Drop Down View Resource To Spinner

        binding.genderNewuser.adapter = spinnerArrayAdapterGender //Set Adapter Of Spinner
        binding.typeNewuser.adapter = spinnerArrayAdapterType     //Set Adapter Of Spinner


    }

}