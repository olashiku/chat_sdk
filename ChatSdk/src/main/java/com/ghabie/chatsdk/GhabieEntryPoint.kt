package com.ghabie.chatsdk

import android.app.Application
import android.content.Intent
import com.ghabie.chatsdk.model.EntryModel
import com.ghabie.chatsdk.storage.PaperPrefs
import com.ghabie.chatsdk.storage.savePref
import com.ghabie.chatsdk.views.activity.SdkActivity
import org.koin.java.KoinJavaComponent.inject

interface GhabieEntryPoint {
    fun startSdk()
    fun setupEntry(entryModel: EntryModel)
}


class GhabieInit(val application: Application) : GhabieEntryPoint {

    val paperPrefs:PaperPrefs by inject(PaperPrefs::class.java)

    override fun startSdk() {
        val intent = Intent(application, SdkActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        application.startActivity(intent)
    }

    override fun setupEntry(entryModel: EntryModel){
        paperPrefs.savePref(PaperPrefs.ORGID, entryModel.orgId)
        paperPrefs.savePref(PaperPrefs.USERID, entryModel.userid)

    }


}