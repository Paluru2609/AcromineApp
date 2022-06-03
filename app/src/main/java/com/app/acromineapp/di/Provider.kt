package com.app.acromineapp.di

import com.app.acromineapp.repository.AcromineRepository
import com.app.acromineapp.repository.AcronymRepositoryImpl
import com.app.acromineapp.repository.remotesource.AcromineRemoteSource
import com.app.acromineapp.repository.remotesource.AcromineRemoteSourceImpl

object Provider {

    fun provideRemoteDataSource(): AcromineRemoteSource =
        AcromineRemoteSourceImpl.create()

    fun provideRepository(acromineRemoteSource: AcromineRemoteSource): AcromineRepository =
        AcronymRepositoryImpl(acromineRemoteSource)
}