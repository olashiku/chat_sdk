package com.olashiku.chatsdk.network

import androidx.annotation.Keep
import com.olashiku.chatsdk.model.response.agent_detail.AgentDetailsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

@Keep
interface Api {
  @GET("open/api/sdk/orgId/{orgId}")
    fun getAgentDetails(@Path("orgId")orgId: String):Deferred<AgentDetailsResponse>
}
