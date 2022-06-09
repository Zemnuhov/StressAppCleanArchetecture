package com.neurotech.domain.models

import java.io.Serializable

data class MarkupDomainModel(
    val markupName: String,
    var timeBegin: String?,
    var timeEnd: String?,
    var firstSource: String?,
    var secondSource: String?,
):Serializable