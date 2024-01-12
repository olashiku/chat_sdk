package com.olashiku.chatsdkandroid.views.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.olashiku.chatsdk.SdkEntryPointImpl
import com.olashiku.chatsdk.model.EntryModel
import com.olashiku.chatsdkandroid.databinding.ActivityTesterBinding


class TesterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTesterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTesterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.backButton.setOnClickListener {

            val openSdk = SdkEntryPointImpl(this)
            openSdk.setupEntry(EntryModel(
                "d58eec4e-40d1-40d9-b294-2b3aedb0e196",
                "Polisl3"))
            openSdk.startSdk()
        }
    }
}