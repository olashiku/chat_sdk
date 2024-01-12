package com.olashiku.chatsdk.views.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.olashiku.chatsdk.R
import com.olashiku.chatsdk.databinding.ActivityMainBinding
import com.olashiku.chatsdk.viewmodel.AgentViewModel
import com.olashiku.chatsdk.viewmodel.SocketViewModel
import org.koin.android.viewmodel.ext.android.viewModel

open class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    private val socketViewModel: SocketViewModel by viewModel()
    private val agentViewModel: AgentViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeNavigation()
        startSocketConnection()
        setupObservers()
    }

    private fun setupObservers() {
        agentViewModel.getAgents()
    }

    private fun startSocketConnection() {
        socketViewModel.apply {
            initSocket()
        }

    }

    private fun initializeNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
    }


    fun checkConnectionStatus(performSocketOperation: () -> Unit) {
        socketViewModel.getConnectionStatus().observe(this) {
            if (it) {
                performSocketOperation.invoke()
            } else {

                startSocketConnection()
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