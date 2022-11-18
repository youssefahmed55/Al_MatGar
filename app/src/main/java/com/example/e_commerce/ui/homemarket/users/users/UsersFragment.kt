package com.example.e_commerce.ui.homemarket.users.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.e_commerce.R
import com.example.e_commerce.adapters.UsersRecyclerAdapter
import com.example.e_commerce.databinding.FragmentUsersBinding
import com.example.e_commerce.pojo.UserModel
import com.example.e_commerce.ui.homemarket.users.ProfileFragment
import com.example.e_commerce.ui.homemarket.users.newuser.NewUserFragment
import com.example.e_commerce.ui.subcategory.SubCategoryFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.consumeAsFlow

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UsersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class UsersFragment : Fragment() {
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
    private lateinit var binding : FragmentUsersBinding
    private val viewModel: UsersViewModel by lazy { ViewModelProvider(this)[UsersViewModel::class.java] }
    private val usersRecyclerAdapter : UsersRecyclerAdapter by lazy { UsersRecyclerAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_users, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        usersRecyclerAdapter.setOnItemClickListener(object : UsersRecyclerAdapter.OnClickOnItem{
            override fun onClick1(userModel: UserModel) {
                val args = Bundle()
                args.putSerializable("userModel", userModel)
                val profileFragment = ProfileFragment()
                profileFragment.arguments = args
                val transaction = activity!!.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.flFragment, profileFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }

        })
        binding.usersRecyclerAdapter = usersRecyclerAdapter


        observeErrorMessage()
        setOnClickOnAddButton()




        return binding.root
    }

    private fun observeErrorMessage() {
        lifecycleScope.launchWhenStarted {
            viewModel.errorMessage.consumeAsFlow().collect{
                Toast.makeText(requireActivity(),it,Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun setOnClickOnAddButton() {
        binding.addFloatingButtonUsersFragment.setOnClickListener {
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, NewUserFragment())
            transaction.addToBackStack(null)
            transaction.commit()

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UsersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UsersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}