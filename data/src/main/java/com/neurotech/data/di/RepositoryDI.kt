package com.neurotech.data.di

import android.content.Context

class RepositoryDI(context: Context) {
    companion object{
        private var _component: RepositoryComponent? = null
        val component get() = _component!!
    }
    init {
        _component = DaggerRepositoryComponent
            .builder()
            .appModule(AppModule(context))
            .build()
    }
}