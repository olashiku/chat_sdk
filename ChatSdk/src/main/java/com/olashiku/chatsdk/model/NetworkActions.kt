package com.olashiku.chatsdk.model

object NetworkActions {
    val auth:String = "auth"
    val newMessage = "new_message"
    val getMessage = "get_message"
    val messageDelivered = "message_delivered"
    val typing = "typing"

}

object NetworkStatus {
    val allow:String = "ALLOW"
    val deny:String = "deny"
}

object UserType {
    val returningUser:String = "returning_user"
    val newUser:String = "new_user"
}

object MessageType{
    val text:String= "text"
    val textCsv:String= "text/csv"
    val textPdf:String= "text/pdf"
    val imagePng:String= "image/png"
    val imageJpeg:String= "image/jpeg"
    val imageGif:String= "image/gif"
    val audioMpeg:String= "audio/mpeg"
    val audioWav:String= "audio/wav"
    val aduioMp3:String= "audio/mp3"
    val videoMp4:String= "video/mp4"
    val videpWebm:String= "video/webm"
}


object Constants{
    const  val platform:String  = "Android"
    const  val me:String  = "me"
    const val you:String  = "you"
    const val userReachable = "user reachable"
    const val messageRead = "READ"
    const val messageUnread = "UNREAD"



}