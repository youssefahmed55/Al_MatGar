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
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentSplashBinding
import com.example.e_commerce.ui.homemarket.homeactivity.HomeActivity
import com.example.e_commerce.ui.login.LoginStates
import com.example.e_commerce.utils.SharedPrefsUtil
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Job

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SplashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "SplashFragment"
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
    }

    private lateinit var binding : FragmentSplashBinding
    private val viewModel: SplashViewModel by lazy { ViewModelProvider(this)[SplashViewModel::class.java] }
    private var job : Job?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_splash, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        // Inflate the layout for this fragment
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            val user = SharedPrefsUtil.getUserModel(requireContext())
            if (user != null && FirebaseAuth.getInstance().currentUser != null){
                activity?.startActivity(Intent(activity, HomeActivity::class.java))
                activity?.finish()
            }else {
                binding.linearSplash.visibility = View.VISIBLE
                setOnClickOnSignInButton()
            }
                 }, 3000)

        return binding.root
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
                     Toast.makeText(requireActivity(),it.error,Toast.LENGTH_SHORT).show()
                 }

                 else -> {}
             }

         }

      }
    }

    override fun onDestroy() {
        job?.cancel()
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
        job?.cancel()
        job = null
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
        render()
    }


    private fun setOnClickOnSignInButton() {
        binding.signInSplash.setOnClickListener { findNavController().navigate(R.id.action_splashFragment_to_signInFragment)}
    }
}