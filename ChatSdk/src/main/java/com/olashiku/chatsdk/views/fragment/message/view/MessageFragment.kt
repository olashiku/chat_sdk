package com.olashiku.chatsdk.views.fragment.message.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.olashiku.chatsdk.R
import com.olashiku.chatsdk.databinding.FragmentMessageBinding
import com.olashiku.chatsdk.model.response.agent_detail.AgentDetailsResponse
import com.olashiku.chatsdk.model.response.connection.ConnectionData
import com.olashiku.chatsdk.storage.PaperPrefs
import com.olashiku.chatsdk.storage.getAnyPref
import com.olashiku.chatsdk.viewmodel.ConnectionViewModel
import com.olashiku.chatsdk.viewmodel.SocketViewModel
import com.olashiku.chatsdk.views.base.BaseFragment
import com.olashiku.chatsdk.views.fragment.message.model.MessageListing
import com.olashiku.chatsdk.views.fragment.message.model.jsonToList
import com.olashiku.chatsdkandroid.utils.Utils
import com.olashiku.chatsdkandroid.utils.updateRecycler
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.sharedViewModel


class MessageFragment : BaseFragment() {
    val connectionViewModel: ConnectionViewModel by sharedViewModel()
    val socketViewModel: SocketViewModel by sharedViewModel()
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
        initClickListener()
        setupObserver()
        setupView()
    }

    private fun setupView() {
        if (paperPrefs.getAnyPref<AgentDetailsResponse>(PaperPrefs.AGENT_DETAILS) != null) {
            val agentDetails = paperPrefs.getAnyPref<AgentDetailsResponse>(PaperPrefs.AGENT_DETAILS)
            binding.recentMessageView.setBackgroundColor(Color.parseColor(agentDetails?.bannerColor))
            binding.sendMessageButton.setBackgroundColor(Color.parseColor(agentDetails?.bannerColor))
        }
    }

    private fun setupObserver() {
        connectionViewModel.getConnectionDetails().observe(viewLifecycleOwner) {
            setupRecycler(regularizeMessages(it))
        }
    }

    private fun regularizeMessages(connectionData: List<ConnectionData>): List<MessageListing> {
        val profileImage =
            paperPrefs.getAnyPref<AgentDetailsResponse>(PaperPrefs.AGENT_DETAILS)?.agentList?.first()?.profile_image_url
        return connectionData.map {
            MessageListing(
                it.connectionName,
                it.agentId,
                getLastAgentMessage(it.message),
                profileImage?:"",
                it.pairedTimeStamp
            )
        }
    }

    private fun getLastAgentMessage(message: String): String {
        val messageList = jsonToList(message)
        return messageList.last().body
    }

    private fun initClickListener() {
        binding.sendMessageButton.setOnClickListener { openFragment(R.id.action_messageFragment_to_chatFragment) }
        binding.backButton.setOnClickListener { popFragment() }
    }

    private fun setupRecycler(connectionData: List<MessageListing>) {
        binding.messageRecyclerView.updateRecycler(
            requireContext(),
            connectionData,
            R.layout.message_blueprint,
            listOf(R.id.titleTextView, R.id.senderTextView, R.id.avatarImageView),
            { innerView, position ->

                val titleTextView = innerView.get(R.id.titleTextView) as TextView
                val senderTextView = innerView.get(R.id.senderTextView) as TextView
                val avatarImageView = innerView.get(R.id.avatarImageView) as ImageView

                if(connectionData.get(position).profileImage.isNotEmpty()){
                    Picasso.get().load(connectionData.get(position).profileImage).into(avatarImageView);
                }
                titleTextView.setText(connectionData.get(position).content)
                senderTextView.setText(
                    getString(
                        R.string.subtitle_text,
                        connectionData.get(position).connectionName,
                       Utils.convertUnixTimestampToAmPm(connectionData.get(position).timeStamp.toLong())
                    )
                )
            }, { position ->
                socketViewModel.getMessages("")


            }, binding.noMessagemageView, binding.noMessageTextView
        )

    }



}