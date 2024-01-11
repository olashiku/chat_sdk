package com.olashiku.chatsdk.views.loader

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window


class Loader (context: Context){

    val progressDialog = Dialog(context)

    init {
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setContentView(com.app.aimatedprogresslib.R.layout.custom_dialog_progress)
        progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setCancelable(false)
    }

     fun showLoader(){
         progressDialog.show()
     }

     fun hideLoader(){
         progressDialog.hide()
     }
}