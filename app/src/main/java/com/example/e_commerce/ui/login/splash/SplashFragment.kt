package com.example.e_commerce.ui.login.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.Constants.NAV_ACTION_LOGIN
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentSplashBinding
import com.example.e_commerce.ui.homemarket.homeactivity.HomeActivity
import com.example.e_commerce.DefaultStates
import com.example.e_commerce.utils.SignedInUtil
import com.example.e_commerce.utils.ToastyUtil
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


private const val TAG = "SplashFragment"
@AndroidEntryPoint
class SplashFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val intentExtra  = activity!!.intent.getIntExtra(NAV_ACTION_LOGIN,0)
        if (intentExtra != 0){
            findNavController().navigate(intentExtra)
        }
    }

    private lateinit var binding : FragmentSplashBinding
    private val viewModel: SplashViewModel by lazy { ViewModelProvider(this)[SplashViewModel::class.java] }
    private var job : Job?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_splash, container, false) //Initialize binding
        binding.lifecycleOwner = this //Set lifecycleOwner
        binding.viewModel = viewModel //Set Variable Of ViewModel (DataBinding)

        Handler(Looper.getMainLooper()).postDelayed({
            if (SignedInUtil.getIsSignIn(context!!) && FirebaseAuth.getInstance().currentUser != null){ // If User Signed In And FireBase Auth Not Equal Null
                activity?.startActivity(Intent(activity, HomeActivity::class.java))  //Intent To Home Activity
                activity?.finish()  //Finish Activity
            }else {
                binding.linearSplash.visibility = View.VISIBLE //Set Linear That Contains Sign In And Explore Buttons Visible
                setOnClickOnSignInButton()                     //Set On Click On Sign In Button
            }
                 }, 2000)  //Delay 2 Seconds

        return binding.root
    }

    private fun render() {
     job = lifecycleScope.launchWhenStarted {
         viewModel.states.collect{
             when(it){
                 is DefaultStates.Success -> {
                     ToastyUtil.successToasty(context!!,it.toastMessage,Toast.LENGTH_SHORT)  //Toast Successful Message
                     activity?.startActivity(Intent(activity, HomeActivity::class.java))     //Intent To Home Activity
                     activity?.finish()                                                      //Finish Activity
                 }
                 is DefaultStates.Error -> {
                     ToastyUtil.errorToasty(context!!,it.error,Toast.LENGTH_SHORT)           //Toast Error Message
                 }

                 else -> {}
             }

         }

      }
    }

    override fun onDestroy() {
        job?.cancel()     //Cancel job
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
        job?.cancel()     //Cancel job
        job = null
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
        render()   //render states From ViewModel
    }


    private fun setOnClickOnSignInButton() {
        binding.signInSplash.setOnClickListener { findNavController().navigate(R.id.action_splashFragment_to_signInFragment)  } //Navigate To SignIn Fragment
    }
}