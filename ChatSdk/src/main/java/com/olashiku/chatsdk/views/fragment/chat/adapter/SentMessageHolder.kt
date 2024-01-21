package com.olashiku.chatsdk.views.fragment.chat.adapter

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.olashiku.chatsdk.R
import com.olashiku.chatsdk.model.response.agent_detail.AgentDetailsResponse
import com.olashiku.chatsdk.model.response.message.Messages
import com.olashiku.chatsdk.storage.PaperPrefs
import com.olashiku.chatsdk.storage.getAnyPref
import com.olashiku.chatsdkandroid.utils.Utils
import org.koin.core.Koin
import org.koin.core.KoinApplication
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