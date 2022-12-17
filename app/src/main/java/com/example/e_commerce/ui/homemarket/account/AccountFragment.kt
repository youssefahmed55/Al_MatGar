package com.example.e_commerce.ui.homemarket.account

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.e_commerce.Constants.NAV_ACTION_LOGIN
import com.example.e_commerce.Constants.PICK_IMAGE
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.R
import com.example.e_commerce.databinding.*
import com.example.e_commerce.ui.homemarket.favorite.FavoritesFragment
import com.example.e_commerce.ui.homemarket.orders.customer.OrdersCustomerFragment
import com.example.e_commerce.ui.login.MainActivity
import com.example.e_commerce.utils.ToastyUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
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
    private val viewModel: AccountViewModel by lazy { ViewModelProvider(this)[AccountViewModel::class.java] }
    private lateinit var bindingBottomSheetGender : BottomsheetgenderBinding
    private lateinit var bindingBottomSheetEmail : BottomsheetemailBinding
    private lateinit var bindingBottomSheetPhone : BottomsheetphoneBinding
    private lateinit var bindingBottomSheetPassword : BottomsheetpasswordBinding
    private lateinit var bindingBottomSheetDate : BottomsheetdateBinding
    private lateinit var bindingBottomSheetLocation : BottomsheetlocationBinding
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private var imageUri : Uri? = null
    private var passwordOfNewEmail : String ?= null
    private var passwordOfNewPassword : String ?= null
    private var newPassword : String ?= null
    private var job : Job ?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity!!.findViewById<RelativeLayout>(R.id.relative1_homeActivity).visibility = View.VISIBLE       //Set Activity's RelativeLayout VISIBLE
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_account, container, false) //Initialize binding
        inti(inflater,container)      //Initialize Variables
        binding.lifecycleOwner = this //Set lifecycleOwner
        binding.viewModel = viewModel //Set Variable Of ViewModel (DataBinding)
        setOnclickOnGender()          //Set On Click On Gender Linear
        setOnclickOnBirthday()        //Set On Click On Birthday Linear
        setOnclickOnEmail()           //Set On Click On Email Linear
        setOnclickOnPhone()           //Set On Click On Phone Linear
        setOnclickOnLocation()        //Set On Click On Location Linear
        setOnclickOnPassword()        //Set On Click On Password Linear
        setOnClickOnProfileImage()    //Set On Click On ProfileImage
        setOnClickOnSaveButton()      //Set On Click On Save Button
        setOnClickOnFavorites()       //Set On Click On Favorites
        setOnClickOnOrders()          //Set On Click On Orders
        setOnClickOnBack()            //Set On Press Back
        observeSignOut()              //Observe Sign Out
        return  binding.root
    }

    private fun setOnClickOnOrders() {
        binding.ordersAccountFragment.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.flFragment, OrdersCustomerFragment()).addToBackStack(null).commit() //Replace OrdersCustomerFragment
        }
    }

    private fun setOnClickOnBack() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {activity!!.finish()}
            }
        activity!!.onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun setOnClickOnFavorites() {
        binding.favoritesAccountFragment.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.flFragment, FavoritesFragment()).addToBackStack(null).commit() //Replace FavoritesFragment
        }
    }

    private fun setOnClickOnSaveButton() {
       binding.saveButtonAccountFragment.setOnClickListener {
           //Save All Changes
           viewModel.saveAllChanges(binding.genderAccountFragment.text.toString().trim()
                                    ,binding.birthdayAccountFragment.text.toString().trim()
                                    ,binding.emailAccountFragment.text.toString().trim()
                                    ,passwordOfNewEmail
                                    ,binding.phoneNumberAccountFragment.text.toString().trim()
                                    ,newPassword
                                    ,passwordOfNewPassword
                                    ,binding.locationAccountFragment.text.toString().trim()
                                    ,imageUri)
       }
    }

    override fun onPause() {
        job?.cancel()   //Cancel job
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        render()  //render states From ViewModel
    }

    private fun render() {
        job = lifecycleScope.launchWhenStarted {
            viewModel.states.collect{
                when(it){
                    is DefaultStates.Success -> {

                        ToastyUtil.successToasty(context!!,getString(R.string.Saved_Successfully), Toast.LENGTH_SHORT) //Toast Successful Message

                        imageUri = null
                        passwordOfNewEmail = null
                        passwordOfNewPassword = null
                        newPassword = null
                        bindingBottomSheetEmail.passwordBottomSheetEmail.setText("")
                        bindingBottomSheetPassword.currentPasswordBottomSheetPassword.setText("")
                        bindingBottomSheetPassword.passwordBottomSheetPassword.setText("")
                        bindingBottomSheetPassword.confirmPasswordBottomSheetPassword.setText("")


                        if (it.toastMessage == "signInAgain"){ //If User Changed His Email Need To Sign In Again
                            viewModel.signOut()
                        }
                    }
                    is DefaultStates.Error -> {
                        ToastyUtil.errorToasty(context!!,it.error, Toast.LENGTH_SHORT) //Toast Error Message
                    }

                    else -> {}
                }
            }
        }
    }

    private fun observeSignOut(){
        viewModel.liveDataSignedOut.observe(viewLifecycleOwner){
            if (it){
                ToastyUtil.infoToasty(context!!,getString(R.string.please_Check_your_email_address), Toast.LENGTH_LONG) //Toast Info
                val intent = Intent(activity, MainActivity::class.java)    //Intent To MainActivity
                intent.putExtra(NAV_ACTION_LOGIN,R.id.action_splashFragment_to_signInFragment)
                activity?.startActivity(intent)
                activity?.finish() //Finish Activity
            }
        }
    }

    private fun setOnClickOnProfileImage() {
        binding.profileImageAccountFragment.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data?.data
            binding.profileImageAccountFragment.setImageURI(imageUri)
        }

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

    private fun setOnclickOnBirthday() {
        binding.birthdayLinearAccountFragment.setOnClickListener {

            bindingBottomSheetDate.calenderBottomSheetDate.setOnDateChangeListener { _, i, i2, i3 ->
                binding.birthdayAccountFragment.text = "$i3-${i2+1}-$i"
                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.setContentView(bindingBottomSheetDate.root)
            bottomSheetDialog.show()
        }

    }

    private fun setOnclickOnEmail() {
        binding.emailLinearAccountFragment.setOnClickListener {
            bottomSheetDialog.setContentView(bindingBottomSheetEmail.root)
            bottomSheetDialog.show()
            bindingBottomSheetEmail.emailBottomSheetEmail.setText(binding.emailAccountFragment.text.toString())
            bottomSheetDialog.setOnCancelListener {
                if (bindingBottomSheetEmail.emailBottomSheetEmail.text.toString().trim().isNotEmpty()&&bindingBottomSheetEmail.passwordBottomSheetEmail.text.toString().trim().isNotEmpty())
                    binding.emailAccountFragment.text =  bindingBottomSheetEmail.emailBottomSheetEmail.text.toString().trim()
                    passwordOfNewEmail = bindingBottomSheetEmail.passwordBottomSheetEmail.text.toString().trim()
            }
        }

    }

    private fun setOnclickOnPhone() {
        binding.phoneNumberLinearAccountFragment.setOnClickListener {
            bottomSheetDialog.setContentView(bindingBottomSheetPhone.root)
            bottomSheetDialog.show()
            bindingBottomSheetPhone.phoneNumberBottomSheetPhone.setText(binding.phoneNumberAccountFragment.text.toString())
            bottomSheetDialog.setOnCancelListener {
                if (bindingBottomSheetPhone.phoneNumberBottomSheetPhone.text.toString().trim().isNotEmpty())
                    binding.phoneNumberAccountFragment.text =  bindingBottomSheetPhone.phoneNumberBottomSheetPhone.text.toString().trim()
            }
        }

    }

    private fun setOnclickOnLocation() {
        binding.locationLinearAccountFragment.setOnClickListener {
            bottomSheetDialog.setContentView(bindingBottomSheetLocation.root)
            bottomSheetDialog.show()
            bindingBottomSheetLocation.locationBottomSheetLocation.setText(binding.locationAccountFragment.text.toString())
            bottomSheetDialog.setOnCancelListener {
                if (bindingBottomSheetLocation.locationBottomSheetLocation.text.toString().trim().isNotEmpty())
                    binding.locationAccountFragment.text =  bindingBottomSheetLocation.locationBottomSheetLocation.text.toString().trim()
            }
        }
    }

    private fun setOnclickOnPassword() {
        binding.changePasswordLinearAccountFragment.setOnClickListener {
            bottomSheetDialog.setContentView(bindingBottomSheetPassword.root)
            bottomSheetDialog.show()
            bottomSheetDialog.setOnCancelListener {
                val newPass = bindingBottomSheetPassword.passwordBottomSheetPassword.text.toString().trim()
                val confirmPass = bindingBottomSheetPassword.confirmPasswordBottomSheetPassword.text.toString().trim()
                if (newPass.isNotEmpty() && confirmPass.isNotEmpty() && bindingBottomSheetPassword.currentPasswordBottomSheetPassword.text.toString().trim().isNotEmpty()){
                    if (newPass == confirmPass){
                        newPassword = newPass
                        passwordOfNewPassword = bindingBottomSheetPassword.currentPasswordBottomSheetPassword.text.toString().trim()
                    }else{
                        Snackbar.make(activity!!.findViewById(R.id.flFragment),getString(R.string.Password_Donot_match),Snackbar.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }



    private fun inti(inflater: LayoutInflater, container: ViewGroup?) {
        //Initialize bottomSheetDialog
        bottomSheetDialog = BottomSheetDialog(context!!, R.style.AppBottomSheetDialogTheme)
        //Initialize bindingBottomSheetGender
        bindingBottomSheetGender = DataBindingUtil.inflate(inflater,R.layout.bottomsheetgender, container, false)
        //Initialize bindingBottomSheetEmail
        bindingBottomSheetEmail = DataBindingUtil.inflate(inflater,R.layout.bottomsheetemail, container, false)
        //Initialize bindingBottomSheetPhone
        bindingBottomSheetPhone = DataBindingUtil.inflate(inflater,R.layout.bottomsheetphone, container, false)
        //Initialize bindingBottomSheetPassword
        bindingBottomSheetPassword = DataBindingUtil.inflate(inflater,R.layout.bottomsheetpassword, container, false)
        //Initialize bindingBottomSheetDate
        bindingBottomSheetDate = DataBindingUtil.inflate(inflater,R.layout.bottomsheetdate, container, false)
        //Initialize bindingBottomSheetLocation
        bindingBottomSheetLocation = DataBindingUtil.inflate(inflater,R.layout.bottomsheetlocation, container, false)
    }




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