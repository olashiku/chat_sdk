package com.ghabie.chatsdk.extensions

import com.google.gson.Gson


inline fun<reified T:Any> String.convertToObject(objectClass:T):T{
 return   Gson().fromJson(this,objectClass::class.java )
}