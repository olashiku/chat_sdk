package com.ghabie.chatsdk.model.response.agent_detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AgentDetailsResponse(
    @SerializedName("agentList")
    @Expose
    val agentList: List<Agent> = emptyList(),
    @SerializedName("authorizedDomains")
    @Expose
    val authorizedDomains: Any = Any(),
    @SerializedName("averageResponseTime")
    @Expose
    val averageResponseTime: Int = 0,
    @SerializedName("bannerColor")
    @Expose
    val bannerColor: String? = "#90ee90",
    @SerializedName("greeting")
    @Expose
    val greeting: String = "Hello world",
    @SerializedName("intro")
    @Expose
    val intro: String = "Hi world",
    @SerializedName("logoUrl")
    @Expose
    val logoUrl: Any = Any(),
    @SerializedName("patternUrl")
    @Expose
    val patternUrl: String = "",
    @SerializedName("preferredName")
    @Expose
    val preferredName: Any = Any()
)