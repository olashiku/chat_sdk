package com.ghabie.chatsdk.repository

import android.annotation.SuppressLint
import com.ghabie.chatsdk.model.response.agent_detail.AgentDetailsResponse
import com.ghabie.chatsdk.network.Api
import com.ghabie.chatsdk.storage.PaperPrefs
import com.ghabie.chatsdk.storage.getStringPref
import com.ghabie.chatsdk.storage.savePref
import com.ghabie.chatsdk.utils.UseCaseResult

interface AgentRepository {
   suspend fun getAgent(agentId:String):UseCaseResult<AgentDetailsResponse>
}

class AgentRepositoryImpl(val api: Api):BaseRepository(),AgentRepository{

    @SuppressLint("SuspiciousIndentation")
    override suspend fun getAgent(agentId:String): UseCaseResult<AgentDetailsResponse> {
    val request = paperPref.getStringPref(PaperPrefs.ORGID)?:""
      return  safeApiCalls(request,api::getAgentDetails,{it.agentList.isNotEmpty()},::saveAgentDetails)
    }

    private fun saveAgentDetails(response: AgentDetailsResponse){
       paperPref.savePref(PaperPrefs.AGENT_DETAILS,response)
    }

}