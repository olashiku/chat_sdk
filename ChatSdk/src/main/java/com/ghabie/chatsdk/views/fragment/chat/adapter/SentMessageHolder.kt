package com.ghabie.chatsdk.views.fragment.chat.adapter

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ghabie.chatsdk.R
import com.ghabie.chatsdk.model.response.agent_detail.AgentDetailsResponse
import com.ghabie.chatsdk.model.response.message.Messages
import com.ghabie.chatsdk.storage.PaperPrefs
import com.ghabie.chatsdk.storage.getAnyPref
import org.koin.java.KoinJavaComponent

private class SentMessageHolder constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    val paperPref: PaperPrefs by KoinJavaComponent.inject(PaperPrefs::class.java)
    var messageText: TextView
    var messageCardView : CardView

    init {
        messageText = itemView.findViewById<View>(R.id.text_gchat_message_me) as TextView
        messageCardView = itemView.findViewById<View>(R.id.card_gchat_message_me) as CardView
    }
    fun bind(message: Messages) {
        messageText.setText(message.content)
        val agentDetails = paperPref.getAnyPref<AgentDetailsResponse>(PaperPrefs.AGENT_DETAILS)
        agentDetails?.let {
            messageCardView.setCardBackgroundColor(Color.parseColor(agentDetails.bannerColor))
        }

    }
}