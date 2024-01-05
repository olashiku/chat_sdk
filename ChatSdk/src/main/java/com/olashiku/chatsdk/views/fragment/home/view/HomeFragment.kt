package com.olashiku.chatsdk.views.fragment.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.olashiku.chatsdk.R
import com.olashiku.chatsdk.databinding.FragmentHomeBinding
import com.olashiku.chatsdk.extensions.hide
import com.olashiku.chatsdk.extensions.show
import com.olashiku.chatsdk.model.NetworkStatus
import com.olashiku.chatsdk.model.UserType
import com.olashiku.chatsdk.viewmodel.AuthenticationViewModel
import com.olashiku.chatsdk.viewmodel.SocketViewModel
import com.olashiku.chatsdk.views.activity.MainActivity
import com.olashiku.chatsdk.views.base.BaseFragment
import com.olashiku.chatsdkandroid.utils.Utils
import org.koin.android.ext.android.inject


class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val socketViewModel: SocketViewModel by inject()
    private val authenticationViewModel: AuthenticationViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoader()
        setupView()
        setupClickListener()
        setupObservers()
    }

    private fun setupObservers() {
        Utils.delayTimer(3000) {
            (activity as MainActivity).checkConnectionStatus {
                socketViewModel.loginUser()
            }
        }

        authenticationViewModel.getLoginResponse().observe(viewLifecycleOwner) {
          hideLoader()
            if (it.status.equals(NetworkStatus.allow)) {
                callOtherApis()
                if (it.data?.userStatus.equals(UserType.returningUser)) {
                    toggleVisibility(true)
                } else {
                    toggleVisibility(false)
                }
            } else {
                Toast.makeText(requireContext(),"Sdk cannot be used, please contact support.",Toast.LENGTH_LONG).show()
                requireActivity().finish()
            }
        }
    }

    private fun callOtherApis() {
        socketViewModel.getMessages()
    }


    private fun toggleVisibility(status: Boolean) {
        if (status) {
            binding.conversationPlaceHolder.hide()
            binding.recentMessagesPlaceHolder.show()
            binding.sendMessagePlaceHolder.show()
        } else {
            binding.conversationPlaceHolder.show()
            binding.recentMessagesPlaceHolder.hide()
            binding.sendMessagePlaceHolder.hide()
        }

    }


    private fun setupClickListener() {
        binding.sendMessageButton.setOnClickListener {
            openFragment(R.id.action_homeFragment_to_chatFragment)
        }

        binding.recentMessagesPlaceHolder.setOnClickListener {
            openFragment(R.id.action_homeFragment_to_messageFragment)

        }

        binding.sendButton.setOnClickListener {
            openFragment(R.id.action_homeFragment_to_chatFragment)
        }

    }

    private fun setupView() {

    }
}