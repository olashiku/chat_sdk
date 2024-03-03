package com.ghabie.chatsdk.views.fragment.chat.view

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.ghabie.chatsdk.R
import com.ghabie.chatsdk.databinding.FragmentChatBinding
import com.ghabie.chatsdk.extensions.getString
import com.ghabie.chatsdk.model.MessageType
import com.ghabie.chatsdk.model.response.agent_detail.AgentDetailsResponse
import com.ghabie.chatsdk.model.response.login.Connection
import com.ghabie.chatsdk.model.response.message.Messages
import com.ghabie.chatsdk.storage.PaperPrefs
import com.ghabie.chatsdk.storage.getAnyPref
import com.ghabie.chatsdk.viewmodel.MessageViewModel
import com.ghabie.chatsdk.viewmodel.SocketViewModel
import com.ghabie.chatsdk.views.base.BaseFragment
import com.ghabie.chatsdk.views.fragment.chat.adapter.MessageListAdapter
import com.ghabie.chatsdkandroid.utils.Utils
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.koin.android.viewmodel.ext.android.sharedViewModel


class ChatFragment : BaseFragment() {

    private lateinit var binding: FragmentChatBinding
    private val messageAdapter = GroupAdapter<GroupieViewHolder>()
    private var mMessageAdapter: MessageListAdapter? = null
    val messageViewModel: MessageViewModel by sharedViewModel()
    val socketViewModel: SocketViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        initClickListener()
        setupObservers()
        getKeyPressed()
        setupEditText()
        setupTextChangeListener()
        setupProfileImage()
    }



     private fun setupProfileImage(){
         binding.profileImage
     }



    private fun setupTextChangeListener() {
         binding.messageEditText.setOnTypingModified { view, isTyping ->
             if(view.text.toString().isNotEmpty()){
                 if(isTyping){
                     socketViewModel.typingMessages(true)
                 }else{
                     socketViewModel.typingMessages(false)
                 }
             }else {
                 socketViewModel.typingMessages(false)
             }
         }
    }

    private fun setupView() {
        val connectionData = paperPrefs.getAnyPref<Connection>(PaperPrefs.CONNECTIONDETAILS)
        binding.nameTextView.text = connectionData?.agentUserName

        if (paperPrefs.getAnyPref<AgentDetailsResponse>(PaperPrefs.AGENT_DETAILS) != null) {
            val agentDetails = paperPrefs.getAnyPref<AgentDetailsResponse>(PaperPrefs.AGENT_DETAILS)
            val recipient = paperPrefs.getAnyPref<Connection>(PaperPrefs.CONNECTIONDETAILS)

            binding.recentMessageView.setBackgroundColor(Color.parseColor(agentDetails?.bannerColor))
            if (recipient?.profileImageUrl.isNullOrEmpty()){
                binding.profileAbbrivationTextView.setText(agentDetails?.agentList?.first()?.first_name?.first().toString())
                binding.profileAbbrivationTextView.setTextColor(Color.parseColor(agentDetails?.bannerColor))

            } else {
                binding.agentBackgroundView.visibility = View.INVISIBLE
                binding.profileAbbrivationTextView.visibility = View.INVISIBLE
                Picasso.get().load(recipient?.profileImageUrl).into(binding.profileImage);
            }
        }
    }

    private fun setupRecycler(messages: List<Messages>) {
        binding.chatRecycler.adapter = messageAdapter
        mMessageAdapter = MessageListAdapter(requireContext(), messages)
        binding.chatRecycler.layoutManager = LinearLayoutManager(requireContext()).apply {
            stackFromEnd = true
            reverseLayout = false
        }
        binding.chatRecycler.setAdapter(mMessageAdapter)
        mMessageAdapter!!.notifyDataSetChanged()
    }

    private fun setupEditText() {
        binding.messageEditText.imeOptions = EditorInfo.IME_ACTION_DONE
        binding.messageEditText.setRawInputType(InputType.TYPE_CLASS_TEXT)
    }

    private fun getKeyPressed() {
        binding.messageEditText.setOnEditorActionListener { _, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER
                || actionId == EditorInfo.IME_ACTION_DONE
            ) {
                messagingAction()
                true
            } else false
        }
    }

    private fun messagingAction() {
        if (binding.messageEditText.getString().isNotEmpty()) {
            socketViewModel.newMessage(binding.messageEditText.text.toString(), MessageType.text, Utils.getCurrentUnixTimestamp())
            binding.messageEditText.text?.clear()
        }
    }

    private fun setupObservers(){
        messageViewModel.getMessagesFromDatabase().observe(viewLifecycleOwner) { data ->
            setupRecycler(data)
        }

        messageViewModel.getTypingMessageStatus().observe(viewLifecycleOwner) {
            if (it.data?.status == true) {
                binding.statusTextView.text = getString(R.string.typing)
            } else {
                binding.statusTextView.text = getString(R.string.online)
            }
        }

         messageViewModel.getOnlineOfflineStatus().observe(viewLifecycleOwner){
             if(it.status){
                 binding.statusTextView.text = getString(R.string.online)
             }else {
                 binding.statusTextView.text = getString(R.string.offline)
             }
         }
    }

    private fun initClickListener() {
        binding.backButton.setOnClickListener {
            popFragment()
        }

        binding.sendMessageButton.setOnClickListener {
            messagingAction()
        }
    }
}