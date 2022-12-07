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
import com.example.e_commerce.BuildConfig
import com.example.e_commerce.Constants.PICK_IMAGE
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.R
import com.example.e_commerce.databinding.*
import com.example.e_commerce.ui.homemarket.favorite.FavoritesFragment
import com.example.e_commerce.ui.login.MainActivity
import com.example.e_commerce.utils.SharedPrefsUtil
import com.example.e_commerce.utils.ToastyUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
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
    private lateinit var bindingBottomsheetemail : BottomsheetemailBinding
    private lateinit var bindingBottomsheetphone : BottomsheetphoneBinding
    private lateinit var bindingBottomsheetpassword : BottomsheetpasswordBinding
    private lateinit var bindingBottomsheetdate : BottomsheetdateBinding
    private lateinit var bindingBottomsheetlocation : BottomsheetlocationBinding
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private var imageUri : Uri? = null
    private var passwordOfNewEmail : String ?= null
    private var passwordOfNewPassword : String ?= null
    private var newPassword : String ?= null
    private var job : Job ?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_account, container, false)
        inti(inflater,container)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        activity!!.findViewById<RelativeLayout>(R.id.relative1_homeActivity).visibility = View.VISIBLE
        setOnclickOnGender()
        setOnclickOnBirthday()
        setOnclickOnEmail()
        setOnclickOnPhone()
        setOnclickOnLocation()
        setOnclickOnPassword()
        setOnClickOnProfileImage()
        setOnClickOnSaveButton()
        setOnClickOnFavorites()
        setOnClickOnBack()
        return  binding.root
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
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.flFragment, FavoritesFragment()).addToBackStack(null).commit()
        }
    }

    private fun setOnClickOnSaveButton() {
       binding.saveButtonAccountFragment.setOnClickListener {

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
        job?.cancel()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        render()
    }

    private fun render() {
        job = lifecycleScope.launchWhenStarted {
            viewModel.states.collect{
                when(it){
                    is DefaultStates.Success -> {

                        ToastyUtil.successToasty(context!!,getString(R.string.Saved_Successfully), Toast.LENGTH_SHORT)

                        imageUri = null
                        passwordOfNewEmail = null
                        passwordOfNewPassword = null
                        newPassword = null
                        bindingBottomsheetemail.passwordBottomSheetEmail.setText("")
                        bindingBottomsheetpassword.currentPasswordBottomSheetPassword.setText("")
                        bindingBottomsheetpassword.passwordBottomSheetPassword.setText("")
                        bindingBottomsheetpassword.confirmPasswordBottomSheetPassword.setText("")


                        if (it.toastMessage == "signInAgain"){
                            FirebaseAuth.getInstance().signOut()
                            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(BuildConfig.GOOGLE_API_KEY)
                                .requestEmail()
                                .build()
                            val googleClient = GoogleSignIn.getClient(context!!, gso)
                            googleClient.signOut()
                            SharedPrefsUtil.clearUserModel(context!!)
                            ToastyUtil.infoToasty(context!!,getString(R.string.please_Check_your_email_address), Toast.LENGTH_SHORT)
                            activity?.startActivity(Intent(activity, MainActivity::class.java))
                            activity?.finish()
                        }
                    }
                    is DefaultStates.Error -> {
                        ToastyUtil.errorToasty(context!!,it.error, Toast.LENGTH_SHORT)
                    }

                    else -> {}
                }
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

            bindingBottomsheetdate.calenderBottomSheetDate.setOnDateChangeListener { calendarView, i, i2, i3 ->
                binding.birthdayAccountFragment.text = "$i3-${i2+1}-$i"
                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.setContentView(bindingBottomsheetdate.root)
            bottomSheetDialog.show()
        }

    }

    private fun setOnclickOnEmail() {
        binding.emailLinearAccountFragment.setOnClickListener {
            bottomSheetDialog.setContentView(bindingBottomsheetemail.root)
            bottomSheetDialog.show()
            bindingBottomsheetemail.emailBottomSheetEmail.setText(binding.emailAccountFragment.text.toString())
            bottomSheetDialog.setOnCancelListener {
                if (bindingBottomsheetemail.emailBottomSheetEmail.text.toString().trim().isNotEmpty()&&bindingBottomsheetemail.passwordBottomSheetEmail.text.toString().trim().isNotEmpty())
                    binding.emailAccountFragment.text =  bindingBottomsheetemail.emailBottomSheetEmail.text.toString().trim()
                    passwordOfNewEmail = bindingBottomsheetemail.passwordBottomSheetEmail.text.toString().trim()
            }
        }

    }

    private fun setOnclickOnPhone() {
        binding.phoneNumberLinearAccountFragment.setOnClickListener {
            bottomSheetDialog.setContentView(bindingBottomsheetphone.root)
            bottomSheetDialog.show()
            bindingBottomsheetphone.phoneNumberBottomSheetPhone.setText(binding.phoneNumberAccountFragment.text.toString())
            bottomSheetDialog.setOnCancelListener {
                if (bindingBottomsheetphone.phoneNumberBottomSheetPhone.text.toString().trim().isNotEmpty())
                    binding.phoneNumberAccountFragment.text =  bindingBottomsheetphone.phoneNumberBottomSheetPhone.text.toString().trim()
            }
        }

    }

    private fun setOnclickOnLocation() {
        binding.locationLinearAccountFragment.setOnClickListener {
            bottomSheetDialog.setContentView(bindingBottomsheetlocation.root)
            bottomSheetDialog.show()
            bindingBottomsheetlocation.locationBottomSheetLocation.setText(binding.locationAccountFragment.text.toString())
            bottomSheetDialog.setOnCancelListener {
                if (bindingBottomsheetlocation.locationBottomSheetLocation.text.toString().trim().isNotEmpty())
                    binding.locationAccountFragment.text =  bindingBottomsheetlocation.locationBottomSheetLocation.text.toString().trim()
            }
        }
    }

    private fun setOnclickOnPassword() {
        binding.changePasswordLinearAccountFragment.setOnClickListener {
            bottomSheetDialog.setContentView(bindingBottomsheetpassword.root)
            bottomSheetDialog.show()
            bottomSheetDialog.setOnCancelListener {
                val newPass = bindingBottomsheetpassword.passwordBottomSheetPassword.text.toString().trim()
                val confirmPass = bindingBottomsheetpassword.confirmPasswordBottomSheetPassword.text.toString().trim()
                if (newPass.isNotEmpty() && confirmPass.isNotEmpty() && bindingBottomsheetpassword.currentPasswordBottomSheetPassword.text.toString().trim().isNotEmpty()){
                    if (newPass == confirmPass){
                        newPassword = newPass
                        passwordOfNewPassword = bindingBottomsheetpassword.currentPasswordBottomSheetPassword.text.toString().trim()
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
        bindingBottomSheetGender = DataBindingUtil.inflate(inflater,R.layout.bottomsheetgender, container, false)
        bindingBottomsheetemail = DataBindingUtil.inflate(inflater,R.layout.bottomsheetemail, container, false)
        bindingBottomsheetphone = DataBindingUtil.inflate(inflater,R.layout.bottomsheetphone, container, false)
        bindingBottomsheetpassword = DataBindingUtil.inflate(inflater,R.layout.bottomsheetpassword, container, false)
        bindingBottomsheetdate = DataBindingUtil.inflate(inflater,R.layout.bottomsheetdate, container, false)
        bindingBottomsheetlocation = DataBindingUtil.inflate(inflater,R.layout.bottomsheetlocation, container, false)
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