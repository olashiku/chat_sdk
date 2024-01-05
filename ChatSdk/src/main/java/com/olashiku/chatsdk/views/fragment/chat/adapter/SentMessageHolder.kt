package com.olashiku.chatsdk.views.fragment.chat.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.olashiku.chatsdk.R
import com.olashiku.chatsdk.model.response.message.Messages

private class SentMessageHolder constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    var messageText: TextView
    var timeText: TextView

    init {
        messageText = itemView.findViewById<View>(R.id.text_gchat_message_me) as TextView
        timeText = itemView.findViewById<View>(R.id.text_gchat_timestamp_me) as TextView
    }

    fun bind(message: Messages) {
        messageText.setText(message.content)
        // Format the stored timestamp into a readable String using method.
        //  timeText.setText(Utils.formatDateTime(message.getCreatedAt()))
    }
}