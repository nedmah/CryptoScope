package com.example.accounts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accounts.domain.model.AccountsModel
import com.example.accounts.domain.model.BlockchainModel
import com.example.accounts.domain.repository.AccountsRepository
import com.example.core.data.settings.SettingsConstants
import com.example.core.domain.settings.SettingsDataStore
import com.example.core.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountsViewModel @Inject constructor(
    private val repository: AccountsRepository,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    private var _state = MutableStateFlow(AccountsScreenState())
    val state = _state.asStateFlow()

    init {
        getAccounts()
    }

    fun onEvent(events: AccountsEvents) {
        when (events) {
            is AccountsEvents.AddAccount -> addAccount(events.name, events.address)
            is AccountsEvents.DeleteAccount -> deleteAccount(events.model)
            AccountsEvents.OnAddPush -> getBlockchains()
            is AccountsEvents.OnAccountSelect -> TODO()
        }
    }

    private fun getAccounts() {
        viewModelScope.launch {
            repository.getAllAccounts().collect { data ->
                _state.value = _state.value.copy(
                    accounts = data
                )
            }
        }
    }

    private fun getBlockchains(){
        viewModelScope.launch(Dispatchers.IO) {
            when(val data = repository.loadBlockchains()){
                is Resource.Error -> _state.value = _state.value.copy(
                    blockchainsError = data.message ?: "error"
                )
                is Resource.Loading -> TODO()
                is Resource.Success -> _state.value = _state.value.copy(
                   blockchains = data.data ?: emptyList()
                )
            }
        }
    }

    private fun addAccount(name: String?, address : String){
        viewModelScope.launch(Dispatchers.IO) {
            name?.let{
                val blockchain = repository.getBlockchainByName(name)
                val account = AccountsModel(address,blockchain.name,blockchain.icon)
                repository.addAccount(account)
            } ?: repository.addAccount(AccountsModel(address))
        }
    }

    private fun saveSelectedAccount(address: String, name: String?){
        name?.let {
            saveSelectedBlockchain(it)
        }
    }

    private fun saveSelectedBlockchain(name: String){
        viewModelScope.launch(Dispatchers.IO) {
            val blockchain = repository.getBlockchainByName(name)
            settingsDataStore.putString(SettingsConstants.SELECTED_BLOCKCHAIN,blockchain.connectionId)
        }
    }

    private fun deleteAccount(account : AccountsModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAccount(account)
        }
    }
}