package com.example.accounts.data

import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.accounts.data.network.BlockchainApi
import com.example.accounts.domain.model.AccountsModel
import com.example.accounts.domain.model.BlockchainModel
import com.example.accounts.domain.repository.AccountsRepository
import com.example.core.data.db.daos.AccountsDao
import com.example.core.data.db.daos.BlockchainsDao
import com.example.core.data.db.daos.MyCoinsDao
import com.example.core.util.Resource
import com.example.core.util.mapExceptionToMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class AccountsRepositoryImpl @Inject constructor(
    private val accountsDao: AccountsDao,
    private val myCoinsDao: MyCoinsDao,
    private val blockchainsDao: BlockchainsDao,
    private val blockchainApi: BlockchainApi
): AccountsRepository {


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun loadBlockchains(): Resource<List<BlockchainModel>> {
        try {
            val blockchains = blockchainsDao.getAllData()
            if (blockchains.isNotEmpty()) {
                val data = blockchains.map { it.toBlockchainModel() }
                return Resource.Success(data = data)
            }
            else {
                val remoteData = blockchainApi.getBlockchains().map { it.toBlockchainEntity() }
                blockchainsDao.upsertAll(remoteData)
                val data = remoteData.map { it.toBlockchainModel() }
                return Resource.Success(data = data)
            }
        } catch (e : Exception){
            return Resource.Error(message = mapExceptionToMessage(e))
        }

    }

    override fun getAllAccounts(): Flow<List<AccountsModel>> {
        return accountsDao.getAllData()
            .map { entities ->
                entities.map { item -> item.toAccountsModel() }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun addAccount(account: AccountsModel) {
        accountsDao.insertAccount(account.toAccountsEntity())
    }

    override suspend fun deleteAccount(account: AccountsModel) {
        accountsDao.deleteAccount(account.address)
    }

    override suspend fun getBlockchainByName(name: String): BlockchainModel {
        return blockchainsDao.getBlockchainByName(name).toBlockchainModel()
    }

    override suspend fun clearMyCoins() {
        myCoinsDao.clearMyCoins()
    }


}