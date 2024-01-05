package com.olashiku.chatsdk.views.fragment.help.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.olashiku.chatsdk.databinding.FragmentHelpBinding
import com.olashiku.chatsdk.views.base.BaseFragment

class HelpFragment : BaseFragment() {

    private lateinit var binding: FragmentHelpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHelpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}