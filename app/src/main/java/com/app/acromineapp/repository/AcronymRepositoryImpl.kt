package com.app.acromineapp.repository

import com.app.acromineapp.repository.remotesource.AcromineRemoteSource
import com.app.acromineapp.repository.remotesource.model.Result
import java.lang.Exception

/**
 * Repository implementation
 */
class AcronymRepositoryImpl(private val remoteSource: AcromineRemoteSource): AcromineRepository {

    override suspend fun getAcronyms(shortForm: String): Result {
        val response = remoteSource.getAcronyms(shortForm)
        return if (response.isSuccessful) {
            Result.Success(response.body())
        } else {
            Result.Error(Exception(response.errorBody()?.string()))
        }
    }
}