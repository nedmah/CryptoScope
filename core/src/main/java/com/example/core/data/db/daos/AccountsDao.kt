package com.example.core.data.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.core.data.db.entities.AccountsEntity
import com.example.core.data.db.entities.BlockchainsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(accountsEntity: AccountsEntity)

    @Query("SELECT * FROM accountsentity")
    fun getAllData() : Flow<List<AccountsEntity>>

    @Query("DELETE FROM accountsentity")
    suspend fun clearAllData()

    @Query("DELETE FROM accountsentity WHERE address = :address")
    suspend fun deleteAccount(address : String)

}