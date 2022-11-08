package com.example.e_commerce.ui.login.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.BuildConfig
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentSignInBinding
import com.example.e_commerce.Constants.GOOGLE_SIGN_IN
import com.example.e_commerce.ui.homemarket.homeactivity.HomeActivity
import com.example.e_commerce.ui.login.LoginStates
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignInFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "SignInFragment"
@AndroidEntryPoint
class SignInFragment : Fragment() {
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
    private lateinit var binding : FragmentSignInBinding
    private val viewModel: SignInViewModel by lazy { ViewModelProvider(this)[SignInViewModel::class.java] }
    private var job : Job ?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_sign_in, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        observeErrorMessage()
        onClickOnGoogle()
        setOnClickOnSignUp()
        setOnClickOnForgetPassword()
        return binding.root
    }

    private fun setOnClickOnForgetPassword() {
        binding.forgetPasswordSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_forgetPasswordFragment)
        }
    }

    private fun onClickOnGoogle() {
        binding.googleSignIn.setOnClickListener {
           val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.GOOGLE_API_KEY)
                .requestEmail()
                .build()

            val signInIntent: Intent = GoogleSignIn.getClient(requireActivity(), gso).signInIntent
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN) {

            try {

                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
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
         job = lifecycleScope.launchWhenStarted {
            viewModel.states.collect{
                when(it){
                    is LoginStates.Success -> {
                        Toast.makeText(requireActivity(),it.toastMessage,Toast.LENGTH_SHORT).show()
                        activity?.startActivity(Intent(activity, HomeActivity::class.java))
                        activity?.finish()
                    }
                    is LoginStates.Error -> {
                        Log.d(TAG, "render: Error")
                        Toast.makeText(requireActivity(),it.error,Toast.LENGTH_SHORT).show()
                    }

                    else -> {}
                }
            }
        }

    }

    override fun onPause() {
        Log.d(TAG, "onPause: ")
        job?.cancel()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
        render()
    }

    private fun observeErrorMessage() {
        viewModel.liveDataErrorMessage.observe(viewLifecycleOwner, Observer {
            when(it){
                R.string.Email_Is_Required -> binding.editTextEmailSignIn.error =  getString(R.string.Email_Is_Required)
                R.string.Password_Is_Required -> binding.editTextPasswordSignIn.error =  getString(R.string.Password_Is_Required)
            }
        })
    }

    private fun setOnClickOnSignUp() {
        binding.signUpTextViewSignIn.setOnClickListener { findNavController().navigate(R.id.action_signInFragment_to_signUpFragment) }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignInFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignInFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}