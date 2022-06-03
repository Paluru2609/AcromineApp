package com.app.acromineapp.repository

import com.app.acromineapp.repository.remotesource.model.Result

/**
 * Repository to communicate between different data sources.
 */
interface AcromineRepository {

    suspend fun getAcronyms(shortForm: String): Result
}