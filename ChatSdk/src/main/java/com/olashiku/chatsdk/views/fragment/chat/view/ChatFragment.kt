package com.olashiku.chatsdk.views.fragment.chat.view

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.olashiku.chatsdk.R
import com.olashiku.chatsdk.databinding.FragmentChatBinding
import com.olashiku.chatsdk.extensions.getString
import com.olashiku.chatsdk.model.MessageType
import com.olashiku.chatsdk.model.response.agent_detail.AgentDetailsResponse
import com.olashiku.chatsdk.model.response.login.Connection
import com.olashiku.chatsdk.model.response.message.Messages
import com.olashiku.chatsdk.storage.PaperPrefs
import com.olashiku.chatsdk.storage.getAnyPref
import com.olashiku.chatsdk.viewmodel.MessageViewModel
import com.olashiku.chatsdk.viewmodel.SocketViewModel
import com.olashiku.chatsdk.views.base.BaseFragment
import com.olashiku.chatsdk.views.fragment.chat.adapter.MessageListAdapter
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions
import org.koin.android.viewmodel.ext.android.sharedViewModel


class ChatFragment : BaseFragment() {
    private lateinit var binding: FragmentChatBinding
    private val messageAdapter = GroupAdapter<GroupieViewHolder>()
    private var mMessageAdapter: MessageListAdapter? = null
    val messageViewModel: MessageViewModel by sharedViewModel()
    val socketViewModel: SocketViewModel by sharedViewModel()
    var delay: Long = 1000 // 1 seconds after user stops typing

    var last_text_edit: Long = 0

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
        setupKeyboardEmojiIcon()
        setupProfileImage()
    }

     private fun setupProfileImage(){
         binding.profileImage
     }

    private fun setupKeyboardEmojiIcon() {
        val emojIcon = EmojIconActions(
            requireContext(),
            view,
            binding.messageEditText,
            binding.smileIcon,
            "#495C66",
            "#DCE1E2",
            "#E6EBEF"
        )
        emojIcon.ShowEmojIcon()
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
            socketViewModel.newMessage(binding.messageEditText.text.toString(), MessageType.text)
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