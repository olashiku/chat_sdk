package com.olashiku.chatsdk.model.response.agent_detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Agent(
    @SerializedName("first_name")
    @Expose
    val first_name: String ="",
    @SerializedName("online")
    @Expose
    val online: Boolean = false,
    @SerializedName("profile_image_url")
    @Expose
    val profile_image_url: String = ""
)