package com.olashiku.chatsdk.views.fragment.chat.view

import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.olashiku.chatsdk.databinding.FragmentChatBinding
import com.olashiku.chatsdk.extensions.getString
import com.olashiku.chatsdk.model.MessageType
import com.olashiku.chatsdk.model.response.message.Messages
import com.olashiku.chatsdk.viewmodel.MessageViewModel
import com.olashiku.chatsdk.viewmodel.SocketViewModel
import com.olashiku.chatsdk.views.base.BaseFragment
import com.olashiku.chatsdk.views.fragment.chat.adapter.MessageListAdapter
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.koin.android.viewmodel.ext.android.sharedViewModel


class ChatFragment : BaseFragment() {

    private lateinit var binding: FragmentChatBinding
    private val messageAdapter = GroupAdapter<GroupieViewHolder>()
    private var mMessageAdapter: MessageListAdapter? = null
    val messageViewModel:MessageViewModel by sharedViewModel()
    val socketViewModel:SocketViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
        setupObservers()
        getKeyPressed()
        setupEditText()
    }

     private fun setupRecycler(messages:List<Messages>){
         binding.chatRecycler.adapter = messageAdapter
         mMessageAdapter = MessageListAdapter(requireContext(), messages)
         binding.chatRecycler.setLayoutManager( LinearLayoutManager(requireContext()))
         binding.chatRecycler.setAdapter(mMessageAdapter)
         mMessageAdapter!!.notifyDataSetChanged()

     }

     private fun setupEditText(){
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
        if(binding.messageEditText.getString().isNotEmpty()){
           socketViewModel.newMessage(binding.messageEditText.text.toString(),MessageType.text)
            binding.messageEditText.text.clear()
        }
    }

    private fun setupObservers() {
        messageViewModel.getMessagesFromDatabase().observe(viewLifecycleOwner) {data ->
            setupRecycler(data)
        }
    }

    private fun initClickListener() {
        binding.backButton.setOnClickListener { popFragment() }
    }
}