package com.olashiku.chatsdk.repository

import com.olashiku.chatsdk.storage.PaperPrefs
import org.koin.java.KoinJavaComponent.inject

open class BaseSocketRepository {
     val paperPref: PaperPrefs by inject(PaperPrefs::class.java)


}