package com.olashiku.chatsdk.views.fragment.chatsupport.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.olashiku.chatsdk.databinding.FragmentChatSupportBinding
import com.olashiku.chatsdk.views.base.BaseFragment


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