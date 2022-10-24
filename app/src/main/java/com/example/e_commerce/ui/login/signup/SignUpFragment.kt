package com.example.e_commerce.ui.login.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentSignUpBinding
import com.example.e_commerce.ui.Constants.GOOGLE_SIGN_IN
import com.example.e_commerce.ui.homemarket.HomeActivity
import com.example.e_commerce.ui.login.LoginStates
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "SignUpFragment"
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
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sign_up, container, false)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        observeErrorMessage()
        render()
        observeGoogleSignInClient()


        return binding.root
    }

    private fun observeGoogleSignInClient() {
        viewModel.googleSignInClient.observe(viewLifecycleOwner, Observer {
            val signInIntent: Intent = it.signInIntent
            startActivityForResult(signInIntent,GOOGLE_SIGN_IN)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                account.idToken?.let { viewModel.firebaseAuthWithGoogle(it) }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(requireActivity(),"Google sign in failed: ${e.message}",Toast.LENGTH_SHORT).show()
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }


    private fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.states.collect{

                when(it){
                    is LoginStates.Loading -> {binding.myProgressSignUp.visibility = View.VISIBLE}
                    is LoginStates.Success -> {
                        Toast.makeText(requireActivity(),it.toastMessage,Toast.LENGTH_SHORT).show()
                        binding.myProgressSignUp.visibility = View.GONE
                        if (it.toastMessage == "Sign In With Google Account Successfully"){
                            activity?.startActivity(Intent(activity, HomeActivity::class.java))
                            activity?.finish()
                        }else {
                            findNavController().popBackStack()
                        }
                    }
                    is LoginStates.Error -> {
                        Toast.makeText(requireActivity(),it.error,Toast.LENGTH_SHORT).show()
                        binding.myProgressSignUp.visibility = View.GONE
                    }

                    else -> {}
                }


            }
        }
    }

    private fun observeErrorMessage() {
        viewModel.liveDataErrorMessage.observe(viewLifecycleOwner, Observer {
            when(it){
                1 -> binding.editTextFullNameSignUp.error =  getString(R.string.FullName_Is_Required)
                2 -> binding.editTextEmailAddressSignUp.error =  getString(R.string.Email_Is_Required)
                3 -> binding.editTextPhoneNumberSignUp.error =  getString(R.string.Phone_Number_Is_Required)
                4 -> binding.editTextPasswordSignUp.error =  getString(R.string.Password_Is_Required)
                5 -> binding.editTextConfirmPasswordSignUp.error =  getString(R.string.Confirm_Password_Is_Required)
                6 -> binding.editTextConfirmPasswordSignUp.error = getString(R.string.Password_Donot_match)
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
         * @return A new instance of fragment SignUpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignUpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}