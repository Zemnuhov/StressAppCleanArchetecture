package com.neurotech.data.modules.storage.firebase.entity

import com.google.firebase.database.IgnoreExtraProperties
import com.neurotech.data.modules.storage.database.entity.UserEntity

@IgnoreExtraProperties
data class UserFirebase(
    val id: String? = null,
    val name: String? = null,
    val dateOfBirth: String? = null,
    val gender: String? = null,
    val tonicAvg: Int? = null,
    val peakInDayNormal: Int? = null,
    val peakInHourNormal: Int? = null,
    val peakNormal: Int? = null
) {
    fun toUserEntity(): UserEntity {
        return UserEntity(
            id!!,
            name!!,
            dateOfBirth,
            gender,
            tonicAvg!!,
            peakInDayNormal!!,
            peakInHourNormal!!,
            peakNormal!!
        )
    }
}