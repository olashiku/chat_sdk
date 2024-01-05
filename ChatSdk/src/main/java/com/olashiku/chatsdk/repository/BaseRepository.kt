package com.olashiku.chatsdk.repository

import com.olashiku.chatsdk.storage.PaperPrefs
import org.koin.core.Koin
import org.koin.java.KoinJavaComponent.inject

open class BaseRepository {
     val paperPref: PaperPrefs by inject(PaperPrefs::class.java)


}