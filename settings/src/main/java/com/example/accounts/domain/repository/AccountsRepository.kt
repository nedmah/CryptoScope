package com.example.accounts.domain.repository

import com.example.accounts.domain.model.AccountsModel
import com.example.accounts.domain.model.BlockchainModel
import com.example.core.data.db.entities.AccountsEntity
import com.example.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {

    suspend fun loadBlockchains() : Resource<List<BlockchainModel>>

    fun getAllAccounts() : Flow<List<AccountsModel>>

    suspend fun addAccount(account: AccountsModel)

    suspend fun deleteAccount(account: AccountsModel)

    suspend fun getBlockchainByName(name: String) : BlockchainModel

    suspend fun clearMyCoins()
}