package com.olashiku.chatsdk.model.response.message

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.olashiku.chatsdk.model.Constants
import com.olashiku.chatsdk.model.NetworkActions
import com.olashiku.chatsdk.model.request.new_message.Message
import com.olashiku.chatsdk.model.request.new_message.MsgReceiver
import com.olashiku.chatsdk.model.request.new_message.NewMessageRequest
import com.olashiku.chatsdkandroid.utils.Utils

@Entity(tableName = "Messages")
data class Messages(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val msgId: String,
    val timeStamp: Long,
    val senderUserId: String,
    val receiverUserId: String,
    val type: String,
    val msgRead: Boolean,
    val content: String,
    val sendBy: String,
    val messageStatus: String,
    val orgId: String,
    val userId: String,
    val agentUserName:String
)

fun Messages.convertMessageToRequest(): NewMessageRequest {
    val message = Message(this.content, Constants.platform, this.type)
    val receiver = MsgReceiver("", this.agentUserName)
    val sender = com.olashiku.chatsdk.model.request.new_message.MsgSender(
        Utils.getDeviceDetails(),
        this.orgId,
        this.userId
    )


    return NewMessageRequest(
        NetworkActions.newMessage,
        message,
        Utils.getUniqueRef(),
        Constants.platform,
        receiver,
        sender,
        0
    )
}