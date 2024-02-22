package com.olashiku.chatsdk.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.olashiku.chatsdk.repository.SocketRepository
import com.olashiku.chatsdk.viewmodel.SocketViewModel
import org.koin.java.KoinJavaComponent

class BackgroundService : Service() {

    val socketViewModel: SocketViewModel by KoinJavaComponent.inject(SocketViewModel::class.java)


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        socketViewModel.initSocket()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}