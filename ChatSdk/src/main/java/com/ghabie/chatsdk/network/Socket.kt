package com.ghabie.chatsdk.network

import com.ghabie.chatsdk.model.request.auth.LoginRequest
import com.ghabie.chatsdk.model.request.connections.ConnectionRequest
import com.ghabie.chatsdk.model.request.get_messages.GetMessagesRequest
import com.ghabie.chatsdk.model.request.new_message.NewMessageRequest
import com.ghabie.chatsdk.model.request.typing.TypingRequest
import com.ghabie.chatsdk.model.response.socket.SocketResponse
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable


interface Socket {
    @Receive
    fun observeWebSocketEvent(): Flowable<WebSocket.Event>

    @Send
    fun login(subscribe: LoginRequest)

     @Send
     fun newMessage(newMessageRequest: NewMessageRequest)


    @Send
    fun typingMessage(typingRequest: TypingRequest)


    @Send
    fun getMessages(getMessagesRequest: GetMessagesRequest)

    @Send
    fun getConnection(connectionRequest: ConnectionRequest)

    @Receive
    fun observeActions(): Flowable<SocketResponse>

}
