package com.olashiku.chatsdk.views.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.olashiku.chatsdk.R
import com.olashiku.chatsdk.service.BackgroundService
import com.olashiku.chatsdk.viewmodel.AgentViewModel
import com.olashiku.chatsdk.viewmodel.SocketViewModel
import org.koin.android.viewmodel.ext.android.viewModel


open class SdkActivity : AppCompatActivity() {

    private val socketViewModel: SocketViewModel by viewModel()
    private val agentViewModel: AgentViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sdk)
        startBackgroundService()
        setupObservers()
    }

    private fun setupObservers() {
        agentViewModel.getAgents()
    }

    private fun startBackgroundService() {
        val serviceIntent = Intent(
            this,
            BackgroundService::class.java
        )
        startService(serviceIntent)
    }


    fun checkConnectionStatus(performSocketOperation: () -> Unit) {
        socketViewModel.getConnectionStatus().observe(this) {
            if (it) {
                performSocketOperation.invoke()
            } else {
                startBackgroundService()
                Toast.makeText(this, getString(R.string.lost_connection), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        socketViewModel.destroySocket()
    }

}