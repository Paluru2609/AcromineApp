package com.app.acromineapp.repository.remotesource

import com.app.acromineapp.repository.remotesource.model.Acromine
import retrofit2.Response

/**
 * Remote data source
 */
interface AcromineRemoteSource {

    suspend fun getAcronyms(shortForm: String): Response<List<Acromine>>
}