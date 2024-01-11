package com.olashiku.chatsdk.repository

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.olashiku.chatsdk.model.NetworkActions
import com.olashiku.chatsdk.model.request.auth.LoginRequest
import com.olashiku.chatsdk.model.request.connections.ConnectionRequest
import com.olashiku.chatsdk.model.request.get_messages.GetMessagesRequest
import com.olashiku.chatsdk.model.request.new_message.NewMessageRequest
import com.olashiku.chatsdk.model.request.typing.TypingRequest
import com.olashiku.chatsdk.model.response.base_response.BaseResponse
import com.olashiku.chatsdk.model.response.message.convertMessageToRequest
import com.olashiku.chatsdk.network.Socket
import com.olashiku.chatsdkandroid.utils.getObject
import com.tinder.scarlet.Message
import com.tinder.scarlet.WebSocket
import io.reactivex.disposables.Disposable

interface SocketRepository {
    suspend fun startSocket()
    fun destroySocket()
    fun isSocketAlive(): Boolean

    /** authentication functions*/
    fun loginUser(loginRequest: LoginRequest)

    /** messages functions*/
    fun newMessage(request: NewMessageRequest)
    fun typingMessage(request: TypingRequest)
    fun getMessage(request: GetMessagesRequest)
    fun getConnectionStatus():MutableLiveData<Boolean>
    fun getConnection(request:ConnectionRequest)
}

class SocketRepositoryImpl(
    private val socket: Socket,
    private val authRepository: AuthRepository,
    private val messageRepository: MessageRepository,
    private val connectionRepository: ConnectionRepository
    ) : SocketRepository {

    lateinit var disposable1: Disposable
    lateinit var disposable2: Disposable
    var hasLoggedIn = false
    val onConnectionStatus = MutableLiveData<Boolean>()

    override suspend fun startSocket() {

        disposable1 = socket.observeWebSocketEvent()
            .doOnError { println(it) }
            .subscribe {
                when (it) {
                    is WebSocket.Event.OnMessageReceived -> {
                        println("OnConnectionMessageReceived")

                        val message = (it.message as Message.Text).value
                        val response = message.getObject<BaseResponse>()
                        println("messages $message")
                        when(response.type){
                            NetworkActions.auth-> {
                                hasLoggedIn = true
                                authRepository.loginUserResponse(message)
                            }
                            NetworkActions.getMessage -> {
                                messageRepository.getMessageResponse(message)
                            }
                            NetworkActions.messageDelivered -> {
                                messageRepository.messageDeliveredResponse(message)
                            }
                            NetworkActions.newMessage ->{
                                messageRepository.newMessageResponse(message)
                            }
                            NetworkActions.typing ->{
                                messageRepository.typingMessageResponse(message)
                            }
                            NetworkActions.connection ->{
                                connectionRepository.saveConnection(message)
                            }
                        }
                    }

                    is WebSocket.Event.OnConnectionClosing -> {
                        println("OnConnectionClosing")
                    }

                    is WebSocket.Event.OnConnectionClosed -> {
                        println("OnConnectionClosed")
                    }

                    is WebSocket.Event.OnConnectionFailed -> {
                        println("OnConnectionFailed")
                        onConnectionStatus.postValue(false)
                    }

                    is WebSocket.Event.OnConnectionOpened<*> -> {
                        println("OnConnectionOpened")
                        onConnectionStatus.postValue(true)
                        println("hasLoggedIn $hasLoggedIn")
                        if(hasLoggedIn){
                            sendBacklogMessages()
                        }
                    }
                }
            }
        disposable2 = socket.observeActions()
            .subscribe { socketData ->
                println("Socket data pushed ${socketData}")
            }
    }

     @SuppressLint("SuspiciousIndentation")
     private fun  sendBacklogMessages(){
     val unsentMessages = messageRepository.getUnsentMessages()
         unsentMessages.forEach {
             newMessage(it.convertMessageToRequest())
         }
     }

    override fun destroySocket() {
        try {
            hasLoggedIn = false
            disposable1.dispose()
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

    override fun loginUser(loginRequest: LoginRequest) {
        socket.login(loginRequest)
    }

    override fun newMessage(request: NewMessageRequest) {
        messageRepository.newMessage(request)
        socket.newMessage(request)
    }

    override fun typingMessage(request: TypingRequest) {
        println("typing_message $request" )
        socket.typingMessage(request)
    }

    override fun getMessage(request: GetMessagesRequest) {
        socket.getMessages(request)
    }

    override fun getConnectionStatus(): MutableLiveData<Boolean> {
     return onConnectionStatus
    }

    override fun getConnection(request: ConnectionRequest) {
        socket.getConnection(request)
    }

    override fun isSocketAlive(): Boolean {
        return !disposable1.isDisposed
    }
}
