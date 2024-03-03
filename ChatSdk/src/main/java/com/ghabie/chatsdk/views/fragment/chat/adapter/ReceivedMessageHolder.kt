package com.ghabie.chatsdk.views.fragment.chat.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ghabie.chatsdk.R
import com.ghabie.chatsdk.model.response.message.Messages

private class ReceivedMessageHolder constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    var nameText: TextView

    init {
        nameText = itemView.findViewById<View>(R.id.text_gchat_message_other) as TextView
    }

    fun bind(message: Messages) {
        nameText.setText(message.content)
    }
}