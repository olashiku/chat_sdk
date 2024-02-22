package com.olashiku.chatsdkandroid.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.UUID


object Utils {
    fun delayTimer(timer:Long = 3000,action:()->Unit){
        Handler(Looper.getMainLooper()).postDelayed({action()},timer)
    }

    fun convertUnixTimestampToDateTime(unixTimestamp: Long): String {
        val dateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC") // Set the time zone to UTC or any desired time zone

        val date = Date(unixTimestamp * 1000L)
        return dateFormat.format(date)
    }

    fun convertUnixTimestampToDate(unixTimestamp: Long): String {

        val currentTime = System.currentTimeMillis()
        val timeDifferenceMillis = currentTime - unixTimestamp * 1000L

        val seconds = timeDifferenceMillis / 1000 % 60
        val minutes = timeDifferenceMillis / (60 * 1000) % 60
        val hours = timeDifferenceMillis / (60 * 60 * 1000) % 24
        val days = timeDifferenceMillis / (24 * 60 * 60 * 1000)

        println("expecting_time days $days hours $hours minutes $minutes seconds $seconds")
        return when {
            days > 0 -> "$days days ago"
            hours > 0 -> "$hours h ago"
            minutes > 0 -> "$minutes m ago"
            seconds > 0 -> "$seconds s ago"
            else -> "just now"
        }
    }

    fun convertUnixTimestampToAmPm(unixTimestamp: Long): String {
        val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getDefault()

        val date = Date(unixTimestamp)
        return dateFormat.format(date)
    }

    fun getUniqueRef():String{
     return UUID.randomUUID().toString()
    }

    fun getCurrentUnixTimestamp(): Long {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Instant.now().atOffset(ZoneOffset.UTC).toEpochSecond()
        } else {
            val calendar = Calendar.getInstance()
            val date = calendar.time
            date.time / 1000
        }
    }

    fun calculateBalance(balance:Float):Float{
        return balance/100
    }
    fun getSocketAction(data: String): String {
        return if(data.contains("action")){
            var obj = JSONObject(data)
            obj["action"] as String
        } else {
            ""
        }
    }

    fun getDeviceDetails():String{
        val deviceModel = Build.MODEL
        val deviceBrand = Build.MANUFACTURER
        val deviceName = Build.DEVICE
        return "$deviceModel $deviceBrand $deviceName"
    }

    fun formatDateString(inputDate: String): String {
        if (inputDate.isEmpty()) {
            return ""
        }

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        try {
            val parsedDate: Date = inputFormat.parse(inputDate) ?: return ""

            // Increment the time by one hour
            val calendar = Calendar.getInstance()
            calendar.time = parsedDate
            calendar.add(Calendar.HOUR_OF_DAY, 1)

            return outputFormat.format(calendar.time)
        } catch (e: Exception) {
            return ""
        }
    }



//    fun String.toDate(): Date {
//        val targetFormat = SimpleDateFormat(RubiesConverter.timeFormat)
//        return  targetFormat.parse(this)
//    }

    fun String.capitalizeWords(): String =
        split(" ").joinToString(" ") { it.lowercase().replaceFirstChar(Char::uppercase) }




//    fun getCurrentTime(): String {
//        val currentDateTime = LocalDateTime.now()
//        val formatter = DateTimeFormatter.ofPattern("HH:mm") // Customize the format as needed
//        return currentDateTime.format(formatter)
//    }


    fun Date.toDateString(targetFormart: String): String {
        val sdf = SimpleDateFormat(targetFormart)
        val calendar = Calendar.getInstance()
        calendar.time = this
        return sdf.format(calendar.time)
    }


    fun Context.openWebBrowser(url:String){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }


    fun <R> convertRequest(request: R): String {
        return Gson().toJson(request)
    }
}
inline fun <reified T> String.getObject(): T {
    val type = object : TypeToken<T>() {}.type
    return Gson().fromJson(this, type)
}

//fun getContactsList( appContext: Context):List<Contact>{
//    val contactList = ArrayList<Contact>()
//    val cursor = appContext.contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
//    if (cursor!!.count > 0) {
//        while (cursor.moveToNext()) {
//            val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
//            val phoneNumber = (cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))).toInt()
//
//            if (phoneNumber > 0) {
//                val cursorPhone = appContext.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)
//
//                if(cursorPhone!!.count > 0) {
//                    while (cursorPhone.moveToNext()) {
//                        val phoneNumValue = cursorPhone.getString(cursorPhone.getColumnIndex(
//                            ContactsContract.CommonDataKinds.Phone.NUMBER))
//                        val phoneName = cursorPhone.getString(cursorPhone.getColumnIndex(
//                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
//                        contactList.add(Contact(phoneNumValue))
//                    }
//                }
//                cursorPhone.close()
//            }
//        }
//    }
//    cursor.close()
//    return contactList
//}
fun convertUTCToTime(time: String,outputPattern: String = "HH:mm:ss",inputPattern: String = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"): String{
    try {
        val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val inputTime = inputFormat.parse(time)
        val outputFormat = SimpleDateFormat(outputPattern, Locale.getDefault())
        outputFormat.timeZone = TimeZone.getDefault()
        inputTime?.let {
            return outputFormat.format(inputTime)
        }?: run {
            return time
        }
    }catch (e: ParseException){
        e.printStackTrace()
        return time
    }
}


//fun getPreviousDateFromToday(): String{
//    val sDate = formatTodaysDate()
//    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
//    val date = dateFormat.parse(sDate)
//    val calendar = Calendar.getInstance()
//    calendar.timeZone = TimeZone.getDefault()
//    if (date != null) {
//        calendar.time = date
//        calendar.add(Calendar.DATE, -1)
//    }
//    return dateFormat.format(calendar.time)
//}

//
//fun formatTodaysDate(): String{
//    val date = LocalDateTime.now()
//    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
//    val currentDate = sdf.format(date)
//    return currentDate.toString()
//}

//fun checkDate(date: String): String {
//    try {
//        val currentDate = convertUTCToTime(date, outputPattern = "dd MMM yyyy")
//        var myDate = ""
//        myDate = when (currentDate) {
//            formatTodaysDate() -> {
//                val dateTime = convertUTCToTime(date, outputPattern = " hh : mm aaa")
//                dateTime
//            }
//            getPreviousDateFromToday() -> {
//                val dateTime = "yesterday"
//                dateTime
//            }
//            else -> convertUTCToTime(date, outputPattern = "dd MMM")
//        }
//
//        return myDate
//    } catch (ex: Exception) {
//        return "now"
//    }
//}



