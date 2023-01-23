package com.neurotech.data.modules.storage.firebase

import android.util.Log
import com.cesarferreira.tempo.toDate
import com.cesarferreira.tempo.toString
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.storage.database.entity.DayResultEntity
import com.neurotech.data.modules.storage.database.entity.HourResultsEntity
import com.neurotech.data.modules.storage.database.entity.ResultEntity
import com.neurotech.data.modules.storage.database.entity.UserEntity
import com.neurotech.data.modules.storage.firebase.entity.ResultFirebase
import com.neurotech.data.modules.storage.firebase.entity.UserFirebase
import com.neurotech.domain.TimeFormat
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class FirebaseData : FirebaseAPI {

    @Inject
    lateinit var firebaseDatabase: FirebaseDatabase

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    private val databaseReference: DatabaseReference
    private val user: FirebaseUser?

    private val scope = CoroutineScope(Dispatchers.IO)
    private val userFlow = MutableStateFlow<UserEntity?>(null)

    init {
        component.inject(this)
        databaseReference = firebaseDatabase.reference
        user = firebaseAuth.currentUser
    }

    override suspend fun getUserInfo(): Flow<UserEntity?> {
        if(user != null) {
            databaseReference.child("users").child(user.uid).get().addOnSuccessListener {
                val map = it.getValue<UserFirebase>()
                userFlow.value = map?.toUserEntity()
            }.addOnFailureListener {
                Log.e("Firebase read error: ", "$it")
                userFlow.value = null
            }
        }
        return userFlow
    }

    override suspend fun setUser(user: UserEntity) {
        databaseReference.child("users").child(user.id).setValue(user)
    }

    override suspend fun writeTenMinuteResult(result: ResultEntity) {
        if(user != null) {
            val key = result.time.toDate(TimeFormat.dateTimeFormatDataBase).toString("yyyy-MM-dd HH:mm:ss_SSS")
            databaseReference.child("tenMinutesData").child(user.uid).child(key).setValue(result)
        }
    }

    override suspend fun writeTenMinuteResults(results: List<ResultEntity>) {
        scope.launch {
            if(user != null){
                results.forEach {
                    val key = it.time.toDate(TimeFormat.dateTimeFormatDataBase).toString("yyyy-MM-dd HH:mm:ss_SSS")
                    databaseReference.child("tenMinutesData").child(user.uid).child(key).setValue(it)
                }
            }
        }
    }


    override suspend fun readTenMinuteResults(): List<ResultEntity> {
        val resultList = mutableListOf<ResultEntity>()
        var isRead = false
        if(user != null) {
            databaseReference
                .child("tenMinutesData")
                .child(user.uid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {
                            val receivedData = it.getValue<ResultFirebase>()
                            if (receivedData != null) {
                                resultList.add(receivedData.toResultEntity())
                            }
                        }
                        isRead = true

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("Firebase read error: ", error.message)
                        Log.e("Firebase read error: ", error.details)
                        Log.e("Firebase read error: ", error.code.toString())
                    }

                }
                )
        }else isRead = true
        return scope.async {
            while (!isRead){}
            return@async resultList
        }.await()
    }

}


