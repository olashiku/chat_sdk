package com.ghabie.chatsdkandroid.views.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ghabie.chatsdk.GhabieInit
import com.ghabie.chatsdk.model.EntryModel
import com.olashiku.chatsdkandroid.databinding.ActivityTesterBinding


class TesterActivity(): AppCompatActivity() {
    private lateinit var binding: ActivityTesterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTesterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.backButton.setOnClickListener {

           GhabieInit(application).apply {
                setupEntry(EntryModel("d58eec4e-40d1-40d9-b294-2b3aedb0e196", "polisl3"))
                startSdk()
            }

        }
    }

}