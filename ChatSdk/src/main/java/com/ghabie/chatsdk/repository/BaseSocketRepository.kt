package com.ghabie.chatsdk.repository

import com.ghabie.chatsdk.storage.PaperPrefs
import org.koin.java.KoinJavaComponent.inject

open class BaseSocketRepository {
     val paperPref: PaperPrefs by inject(PaperPrefs::class.java)


}