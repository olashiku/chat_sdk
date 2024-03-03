package com.ghabie.chatsdk.viewmodel

import com.ghabie.chatsdk.model.response.agent_detail.AgentDetailsResponse
import com.ghabie.chatsdk.network.SingleLiveEvent
import com.ghabie.chatsdk.repository.AgentRepository
import com.ghabie.chatsdk.storage.PaperPrefs
import com.ghabie.chatsdk.storage.getStringPref

class AgentViewModel(val agentRepository: AgentRepository):BaseViewModel() {

    val agentDetailsResponse = SingleLiveEvent<AgentDetailsResponse>()

    fun getAgents(){
        val request = paperPrefs.getStringPref(PaperPrefs.ORGID)?:""
        println("requestDetails $request")
        return apiRequestNew(request, agentRepository::getAgent, agentDetailsResponse, { it.greeting })
    }
}