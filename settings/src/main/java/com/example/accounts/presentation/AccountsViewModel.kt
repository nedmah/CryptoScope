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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
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
            is AccountsEvents.OnAccountSelect -> saveSelectedAccount(events.model)
        }
    }

    private fun getAccounts() {
        viewModelScope.launch {

            combine(
                repository.getAllAccounts(),
                settingsDataStore.getStringFlow(SettingsConstants.SELECTED_WALLET_ADDRESS)
            ) { accounts, selectedAddress ->

                if (accounts.size == 1) {
                    val item = accounts.first()
                    settingsDataStore.putString(
                        SettingsConstants.SELECTED_WALLET_ADDRESS,
                        item.address
                    )
                    item.blockChain?.let {
                        val chain = repository.getBlockchainByName(it)
                        settingsDataStore.putString(SettingsConstants.SELECTED_BLOCKCHAIN, chain.connectionId)
                    }
                    accounts.map { it.copy(isSelected = true) }
                } else {
                    accounts.map { account ->
                        account.copy(isSelected = account.address == selectedAddress)
                    }
                }
            }.collect { updatedAccounts ->
                _state.value = _state.value.copy(
                    accounts = updatedAccounts
                )
            }
        }
    }

    private fun getBlockchains() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val data = repository.loadBlockchains()) {
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

    private fun addAccount(name: String?, address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            name?.let {
                val blockchain = repository.getBlockchainByName(name)
                val account = AccountsModel(address, blockchain.name, blockchain.icon)
                repository.addAccount(account)
            } ?: repository.addAccount(AccountsModel(address))
        }
    }

    private fun deleteAccount(account: AccountsModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAccount(account)
        }
    }

    private fun saveSelectedAccount(account: AccountsModel) {
        viewModelScope.launch(Dispatchers.IO) {

            settingsDataStore.clear(listOf(SettingsConstants.WALLET_LAST_UPDATED, SettingsConstants.SELECTED_BLOCKCHAIN))
            repository.clearMyCoins()

            account.blockChain?.let { name ->
                val blockchain = repository.getBlockchainByName(name)
                println("saveSelectedAccount : $blockchain")
                settingsDataStore.putString(
                    SettingsConstants.SELECTED_BLOCKCHAIN,
                    blockchain.connectionId
                )
            }
            settingsDataStore.putString(
                SettingsConstants.SELECTED_WALLET_ADDRESS,
                account.address
            )


        }
    }


}