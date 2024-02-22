package com.olashiku.chatsdk.views

import android.app.Application
import android.content.Intent
import com.olashiku.chatsdk.GhabieEntryPoint
import com.olashiku.chatsdk.model.EntryModel
import com.olashiku.chatsdk.storage.PaperPrefs
import com.olashiku.chatsdk.storage.savePref
import com.olashiku.chatsdk.views.activity.SdkActivity
import org.koin.java.KoinJavaComponent


class SdkEntryPointImpl(val application: Application) : GhabieEntryPoint {

    val paperPrefs: PaperPrefs by KoinJavaComponent.inject(PaperPrefs::class.java)

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