package com.app.acromineapp.repository.remotesource.api

import com.app.acromineapp.repository.remotesource.model.Acromine
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Rest service for retrieving acronyms
 */
interface AcromineRestService {

    @GET("software/acromine/dictionary.py")
    suspend fun getAcronyms(@Query("sf") shortForm: String): Response<List<Acromine>>
}