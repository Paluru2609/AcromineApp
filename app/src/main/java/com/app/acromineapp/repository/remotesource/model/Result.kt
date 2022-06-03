package com.app.acromineapp.repository.remotesource.model

/**
 * Sealed class to handle Success and Error use cases
 */
sealed class Result {

    data class Success(val acromineList: List<Acromine>?): Result()
    data class Error(val exception: Exception): Result()
}



