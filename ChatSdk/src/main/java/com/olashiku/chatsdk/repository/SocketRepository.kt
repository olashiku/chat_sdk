package com.olashiku.chatsdk.repository

import com.olashiku.chatsdk.model.NetworkActions
import com.olashiku.chatsdk.model.request.auth.LoginRequest
import com.olashiku.chatsdk.model.request.get_messages.GetMessagesRequest
import com.olashiku.chatsdk.model.request.new_message.NewMessageRequest
import com.olashiku.chatsdk.model.request.typing.TypingRequest
import com.olashiku.chatsdk.model.response.base_response.BaseResponse
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
}

class SocketRepositoryImpl(
    private val socket: Socket,
    private val authRepository: AuthRepository,
    private val messageRepository: MessageRepository) : SocketRepository {

    lateinit var disposable1: Disposable
    lateinit var disposable2: Disposable

    override suspend fun startSocket() {

        disposable1 = socket.observeWebSocketEvent()
            .doOnError { println(it) }
            .subscribe {
                when (it) {
                    is WebSocket.Event.OnMessageReceived -> {
                        val message = (it.message as Message.Text).value
                        val response = message.getObject<BaseResponse>()
                        println("messages $message")
                        when(response.type){
                            NetworkActions.auth-> {
                                authRepository.loginUserResponse(message)
                            }
                            NetworkActions.getMessage -> {
                                messageRepository.getMessageResponse(message)
                            }
                            NetworkActions.messageDelivered -> {
                                messageRepository.messageDeliveredResponse(message)
                            }
                            NetworkActions.typing ->{
                                messageRepository.typingMessageResponse(message)
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
                    }

                    is WebSocket.Event.OnConnectionOpened<*> -> {
                        println("OnConnectionOpened")
                    }
                }
            }
        disposable2 = socket.observeActions()
            .subscribe { socketData ->
                println("Socket data pushed ${socketData}")
            }
    }

    override fun destroySocket() {
        try {
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
        socket.typingMessage(request)
    }

    override fun getMessage(request: GetMessagesRequest) {
        socket.getMessages(request)
    }


    override fun isSocketAlive(): Boolean {
        return !disposable1.isDisposed
    }
}
