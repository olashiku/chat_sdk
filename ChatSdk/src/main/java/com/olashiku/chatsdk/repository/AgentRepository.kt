package com.olashiku.chatsdk.repository

import android.annotation.SuppressLint
import com.olashiku.chatsdk.model.response.agent_detail.AgentDetailsResponse
import com.olashiku.chatsdk.network.Api
import com.olashiku.chatsdk.storage.PaperPrefs
import com.olashiku.chatsdk.storage.getStringPref
import com.olashiku.chatsdk.storage.savePref
import com.olashiku.chatsdk.utils.UseCaseResult

interface AgentRepository {
   suspend fun getAgent(agentId:String):UseCaseResult<AgentDetailsResponse>
}

class AgentRepositoryImpl(val api: Api):BaseRepository(),AgentRepository{

    @SuppressLint("SuspiciousIndentation")
    override suspend fun getAgent(agentId:String): UseCaseResult<AgentDetailsResponse> {
    val request = paperPref.getStringPref(PaperPrefs.ORGID)
      return  safeApiCalls(request,api::getAgentDetails,{it.agentList.isNotEmpty()},::saveAgentDetails)
    }

    private fun saveAgentDetails(response: AgentDetailsResponse){
       paperPref.savePref(PaperPrefs.AGENT_DETAILS,response)
    }

}