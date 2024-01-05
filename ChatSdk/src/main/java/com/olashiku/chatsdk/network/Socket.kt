package com.olashiku.chatsdk.network

import com.olashiku.chatsdk.model.request.auth.LoginRequest
import com.olashiku.chatsdk.model.request.get_messages.GetMessagesRequest
import com.olashiku.chatsdk.model.request.new_message.NewMessageRequest
import com.olashiku.chatsdk.model.request.typing.TypingRequest
import com.olashiku.chatsdk.model.response.get_messages.GetMessagesResponse
import com.olashiku.chatsdk.model.response.login.LoginResponse
import com.olashiku.chatsdk.model.response.message_status.MessageStatusResponse
import com.olashiku.chatsdk.model.response.new_message.NewMessageResponse
import com.olashiku.chatsdk.model.response.socket.SocketResponse
import com.olashiku.chatsdk.model.response.typing.TypingResponse
import com.olashiku.chatsdk.model.response.usertype.UserTypeResponse
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable


interface Socket {

    @Receive
    fun observeWebSocketEvent(): Flowable<WebSocket.Event>

    @Receive
    fun getUserType():Flowable<String>

    @Send
    fun login(subscribe: LoginRequest)

    @Receive
    fun login(): Flowable<LoginResponse>

     @Send
     fun newMessage(newMessageRequest: NewMessageRequest)

     @Receive
     fun newMessage():Flowable<NewMessageResponse>

    @Receive
    fun messageDelivered():Flowable<MessageStatusResponse>

    @Send
    fun typingMessage(typingRequest: TypingRequest)

    @Receive
    fun typingMessage():Flowable<TypingResponse>

    @Send
    fun getMessages(getMessagesRequest: GetMessagesRequest)

    @Receive
    fun getMessages():Flowable<GetMessagesResponse>

    @Receive
    fun getMessage():Flowable<NewMessageRequest>

    @Receive
    fun observeActions(): Flowable<SocketResponse>

}
