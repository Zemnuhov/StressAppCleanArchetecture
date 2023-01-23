package com.neurotech.data.modules.storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.neurotech.data.modules.storage.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Insert
    fun addUser(userEntity: UserEntity)

//    @Query("")
//    fun getUserNormalParameters():Flow<UserParameterEntity>

    @Query("SELECT * FROM UserEntity")
    fun getUser(): UserEntity?

    @Query("SELECT * FROM UserEntity")
    fun getUserFlow(): Flow<UserEntity?>

    @Query("SELECT id FROM UserEntity")
    fun getUserId(): Int
//
//    @Query("SELECT tonicAvg, peakNormal, peakInHourNormal, peakInDayNormal FROM UserEntity")
//    fun getUserParameters(): Flow<UserParameterEntity>

    @Query("SELECT COUNT(*) FROM UserEntity")
    fun countUser(): Int

    @Query("DELETE FROM UserEntity")
    fun unregisterUser()

    @Query("UPDATE UserEntity SET dateOfBirth = :birthDate")
    fun setBirthDate(birthDate: String)

    @Query("UPDATE UserEntity SET gender = :gender")
    fun setGender(gender: String)

    @Update
    fun updateUser(userEntity: UserEntity)

    @Transaction
    fun insertUser(userEntity: UserEntity): Boolean{
        if(countUser()==0){
            addUser(userEntity)
            return true
        }
        return false
    }

}