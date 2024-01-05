package com.olashiku.chatsdk.viewmodel

import androidx.lifecycle.ViewModel
import com.olashiku.chatsdk.storage.PaperPrefs
import org.koin.java.KoinJavaComponent

open class BaseViewModel: ViewModel() {
    val paperPrefs: PaperPrefs by KoinJavaComponent.inject(PaperPrefs::class.java)

}