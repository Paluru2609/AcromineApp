package com.app.acromineapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.app.acromineapp.repository.AcromineRepository
import com.app.acromineapp.repository.remotesource.model.Result

/**
 * View model to communicate to data sources with repository
 */
class AcromineViewModel(val repository: AcromineRepository) : ViewModel() {

    fun getAcronyms(shortForm: String): LiveData<Result> = liveData {
        val data = repository.getAcronyms(shortForm)
        try {
            emit(data)
        } catch (exception: Exception) {
            emit(Result.Error(exception))
        }
    }
}

class AcromineViewModelFactory(private val repository: AcromineRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AcromineViewModel::class.java)) {
            return AcromineViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
