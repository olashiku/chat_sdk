package com.olashiku.chatsdk.views.fragment.chat.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.olashiku.chatsdk.R
import com.olashiku.chatsdk.model.response.message.Messages

private class ReceivedMessageHolder  constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    var messageText: TextView
    var timeText: TextView
    var nameText: TextView
    var profileImage: ImageView

    init {
        messageText = itemView.findViewById<View>(R.id.text_gchat_user_other) as TextView
        timeText = itemView.findViewById<View>(R.id.text_gchat_timestamp_other) as TextView
        nameText = itemView.findViewById<View>(R.id.text_gchat_message_other) as TextView
        profileImage = itemView.findViewById<View>(R.id.image_gchat_profile_other) as ImageView
    }

    fun bind(message: Messages) {
        messageText.setText(message.content)
        // Format the stored timestamp into a readable String using method.
        //   timeText.setText(Utils.formatDateTime(message.getCreatedAt()))
        //    nameText.setText(message.getSender().getNickname())
        // Insert the profile image from the URL into the ImageView.
        //  Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage)
    }
}