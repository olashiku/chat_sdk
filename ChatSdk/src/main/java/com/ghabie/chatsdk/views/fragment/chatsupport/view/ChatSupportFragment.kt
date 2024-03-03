package com.ghabie.chatsdk.views.fragment.chatsupport.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ghabie.chatsdk.databinding.FragmentChatSupportBinding
import com.ghabie.chatsdk.views.base.BaseFragment


class ChatSupportFragment : BaseFragment() {

    private lateinit var binding: FragmentChatSupportBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatSupportBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}