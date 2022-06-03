package com.app.acromineapp.repository.remotesource

import com.app.acromineapp.repository.remotesource.api.AcromineRestService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Remote data source implementation to build retrofit instance and retrieve response from apis
 */
class AcromineRemoteSourceImpl(private val acromineRestService: AcromineRestService) : AcromineRemoteSource {
    companion object {
        fun create(): AcromineRemoteSource {
            // Initialization of Retrofit instance
            val retrofit = Retrofit.Builder()
                .baseUrl("http://www.nactem.ac.uk/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(AcromineRestService::class.java)
            return AcromineRemoteSourceImpl(service)
        }
    }

    override suspend fun getAcronyms(shortForm: String) = acromineRestService.getAcronyms(shortForm)
}