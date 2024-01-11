package com.olashiku.chatsdk.model.response.agent_detail

data class AgentDetailsResponse(
    val agentList: List<Agent>,
    val authorizedDomains: Any,
    val averageResponseTime: Int,
    val bannerColor: String,
    val greeting: String,
    val intro: String,
    val logoUrl: Any,
    val patternUrl: String,
    val preferredName: Any
)