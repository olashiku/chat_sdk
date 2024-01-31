package com.olashiku.chatsdk.views.fragment.home.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.olashiku.chatsdk.R
import com.olashiku.chatsdk.databinding.FragmentHomeFinalBinding
import com.olashiku.chatsdk.extensions.hide
import com.olashiku.chatsdk.extensions.show
import com.olashiku.chatsdk.model.NetworkStatus
import com.olashiku.chatsdk.model.UserType
import com.olashiku.chatsdk.model.response.agent_detail.Agent
import com.olashiku.chatsdk.model.response.agent_detail.AgentDetailsResponse
import com.olashiku.chatsdk.model.response.login.Connection
import com.olashiku.chatsdk.storage.PaperPrefs
import com.olashiku.chatsdk.storage.getAnyPref
import com.olashiku.chatsdk.viewmodel.AuthenticationViewModel
import com.olashiku.chatsdk.viewmodel.MessageViewModel
import com.olashiku.chatsdk.viewmodel.SocketViewModel
import com.olashiku.chatsdk.views.activity.SdkActivity
import com.olashiku.chatsdk.views.base.BaseFragment
import com.olashiku.chatsdkandroid.utils.Utils.capitalizeWords
import com.olashiku.chatsdkandroid.utils.Utils.convertUnixTimestampToAmPm
import com.olashiku.chatsdkandroid.utils.Utils.convertUnixTimestampToDate
import com.olashiku.chatsdkandroid.utils.Utils.convertUnixTimestampToDateTime
import com.olashiku.chatsdkandroid.utils.updateRecycler
import com.olashiku.chatsdkandroid.utils.updateRecyclerHorizontal
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel


class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeFinalBinding
    private val socketViewModel: SocketViewModel by inject()
    private val authenticationViewModel: AuthenticationViewModel by inject()
    val messageViewModel: MessageViewModel by sharedViewModel()
    private  var dataFetched = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeFinalBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener()
        setupObservers()
        setupView()
    }

    private fun setupObservers() {

        (activity as SdkActivity).checkConnectionStatus {
            socketViewModel.loginUser()
        }


        messageViewModel.getMessagesFromDatabase().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                toggleVisibility(true)
                binding.previousMessageTextView.text = it.last().content
                setupAgentDetails(convertUnixTimestampToAmPm(it.last().timeStamp))
            } else {
                toggleVisibility(false)
            }
        }

        authenticationViewModel.getLoginResponse().observe(viewLifecycleOwner) {
            if (it.status.equals(NetworkStatus.allow)) {
                if(!dataFetched){
                    setupView()
                    callOtherApis()

                    if (it.data?.userStatus.equals(UserType.returningUser)) {
                        toggleVisibility(true)
                    } else {
                        toggleVisibility(false)
                    }
                }
                dataFetched = true


            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.contact_support),
                    Toast.LENGTH_LONG
                ).show()
                requireActivity().finish()
            }
        }
    }

    private fun callOtherApis() {
        val recipient = paperPrefs.getAnyPref<Connection>(PaperPrefs.CONNECTIONDETAILS)
        socketViewModel.getMessages(recipient?.agentUserName ?: "")
        socketViewModel.getConnection()
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

        binding.sendMessagePlaceHolder.setOnClickListener {
            openFragment(R.id.action_homeFragment_to_chatFragment)
        }
    }

    private fun setupAgentDetails(currentTime:String){
        paperPrefs.getAnyPref<Connection>(PaperPrefs.CONNECTIONDETAILS)?.let {
            binding.senderTextView2.setText(it.firstName?.capitalizeWords()+"・" +currentTime)

        }


    }

    private fun setupView() {
        if (paperPrefs.getAnyPref<AgentDetailsResponse>(PaperPrefs.AGENT_DETAILS) != null) {
            paperPrefs.getAnyPref<AgentDetailsResponse>(PaperPrefs.AGENT_DETAILS)?.let { agentDetails ->
                val recipient = paperPrefs.getAnyPref<Connection>(PaperPrefs.CONNECTIONDETAILS)

                binding.welcomeMessageTextView.text = agentDetails.greeting
                binding.welcomeMessageTextView2.text = agentDetails.intro
                binding.responseTimeTextView.text = getString(
                    R.string.average_response_time,
                    agentDetails.averageResponseTime.toString()
                )
                binding.bannerBackground.setBackgroundColor(Color.parseColor(agentDetails.bannerColor))
                binding.sendMessageButton.setBackgroundColor(Color.parseColor(agentDetails.bannerColor))
                binding.sendButton.setColorFilter(
                    Color.parseColor(agentDetails.bannerColor),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
                binding.imageView7.setColorFilter(
                    Color.parseColor(agentDetails.bannerColor),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
                binding.imageView9.setColorFilter(
                    Color.parseColor(agentDetails.bannerColor),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
                binding.imageView10.setColorFilter(
                    Color.parseColor(agentDetails.bannerColor),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
                binding.imageView12.setColorFilter(
                    Color.parseColor(agentDetails.bannerColor),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
                binding.imageView13.setColorFilter(
                    Color.parseColor(agentDetails.bannerColor),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
                binding.imageView14.setColorFilter(
                    Color.parseColor(agentDetails.bannerColor),
                    android.graphics.PorterDuff.Mode.SRC_IN
                );

                if (recipient?.profileImageUrl.isNullOrEmpty()){
                    binding.agentBackgroundView.setColorFilter(Color.parseColor(agentDetails.bannerColor), android.graphics.PorterDuff.Mode.SRC_IN)
                    binding.profileAbbrivationTextView.setText(agentDetails.agentList.first().first_name.first().toString())
                } else {
                    binding.agentBackgroundView.visibility = View.INVISIBLE
                    binding.profileAbbrivationTextView.visibility = View.INVISIBLE
                    Picasso.get().load(recipient?.profileImageUrl).into(binding.agentImageView);
                }
                setupAgentImageRecycler(agentDetails.agentList)
            }

        }

    }

    private fun setupAgentImageRecycler(agent: List<Agent>) {
        binding.agentImageRecycler.updateRecyclerHorizontal(requireContext(), agent, R.layout.agent_blueprint,
            listOf(R.id.profile_image), { innerView, position ->
                val profileImage = innerView.get(R.id.profile_image) as ImageView
                Picasso.get().load(agent.get(position).profile_image_url).into(profileImage);
            }, {})

    }
}