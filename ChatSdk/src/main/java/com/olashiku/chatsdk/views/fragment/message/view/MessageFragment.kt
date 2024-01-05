package com.olashiku.chatsdk.views.fragment.message.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.olashiku.chatsdk.R
import com.olashiku.chatsdk.databinding.FragmentMessageBinding
import com.olashiku.chatsdkandroid.utils.updateRecycler
import com.olashiku.chatsdk.views.base.BaseFragment
import com.olashiku.chatsdk.views.fragment.message.model.getMessages

class MessageFragment : BaseFragment() {

    private lateinit var binding: FragmentMessageBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClickListener()
    }

    private fun initClickListener() {
        binding.sendMessageButton.setOnClickListener { openFragment(R.id.action_messageFragment_to_chatSupportFragment) }
    }

    private fun initView() {
        binding.messageRecyclerView.updateRecycler(
            requireContext(),
            emptyList(),
            R.layout.message_blueprint,
            listOf(R.id.titleTextView, R.id.senderTextView, R.id.avatarImageView),
            { innerView, position ->

                val titleTextView = innerView.get(R.id.titleTextView) as TextView
                val senderTextView = innerView.get(R.id.senderTextView) as TextView
                val avatarImageView = innerView.get(R.id.avatarImageView) as ImageView

                avatarImageView.setImageResource(getMessages().get(position).image)
                titleTextView.setText(getMessages().get(position).message)
                senderTextView.setText(
                    getString(
                        R.string.subtitle_text,
                        getMessages().get(position).recipient,
                        getMessages().get(position).timeStamp
                    )
                )

            }, { position -> }, binding.noMessagemageView, binding.noMessageTextView
        )

    }

}