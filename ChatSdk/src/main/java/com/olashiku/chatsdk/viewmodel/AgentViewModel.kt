package com.olashiku.chatsdk.viewmodel

import com.olashiku.chatsdk.model.response.agent_detail.AgentDetailsResponse
import com.olashiku.chatsdk.network.SingleLiveEvent
import com.olashiku.chatsdk.repository.AgentRepository
import com.olashiku.chatsdk.repository.safeApiCall
import com.olashiku.chatsdk.storage.PaperPrefs
import com.olashiku.chatsdk.storage.getStringPref

class AgentViewModel(val agentRepository: AgentRepository):BaseViewModel() {

    val agentDetailsResponse = SingleLiveEvent<AgentDetailsResponse>()

    fun getAgents(){
        val request = paperPrefs.getStringPref(PaperPrefs.ORGID)?:""
        println("requestDetails $request")
        return apiRequestNew(request, agentRepository::getAgent, agentDetailsResponse, { it.greeting })
    }
}