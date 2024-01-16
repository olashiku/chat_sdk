package com.olashiku.chatsdk

import android.app.Activity
import android.content.Intent
import com.olashiku.chatsdk.model.EntryModel
import com.olashiku.chatsdk.storage.PaperPrefs
import com.olashiku.chatsdk.storage.savePref
import com.olashiku.chatsdk.views.activity.MainActivity
import com.olashiku.chatsdkandroid.utils.Utils
import org.koin.java.KoinJavaComponent.inject

interface SdkEntryPoint {
    fun startSdk()
    fun setupEntry(entryModel: EntryModel)
}

class SdkEntryPointImpl(val activity: Activity) : SdkEntryPoint {

    val paperPrefs:PaperPrefs by inject(PaperPrefs::class.java)

    override fun startSdk() {
        Utils.delayTimer {
            activity.startActivity(Intent(activity, MainActivity::class.java))
        }
    }

    override fun setupEntry(entryModel: EntryModel){
        paperPrefs.savePref(PaperPrefs.ORGID, entryModel.orgId)
        paperPrefs.savePref(PaperPrefs.USERID, entryModel.userid)

    }


}