package com.app.acromineapp.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.acromineapp.R
import com.app.acromineapp.adapter.AcronymsAdapter
import com.app.acromineapp.di.Provider
import com.app.acromineapp.repository.remotesource.model.Result
import com.app.acromineapp.viewmodel.AcromineViewModel
import com.app.acromineapp.viewmodel.AcromineViewModelFactory

class MainActivity : AppCompatActivity(), Observer<Result> {

    private lateinit var acronymsAdapter: AcronymsAdapter
    private lateinit var viewModel: AcromineViewModel
    private lateinit var viewModelFactory: AcromineViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val acronymEditext = findViewById<EditText>(R.id.acronym)
        val searchButton = findViewById<Button>(R.id.search)
        val recyclerView = findViewById<RecyclerView>(R.id.full_form_list)
        acronymsAdapter = AcronymsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = acronymsAdapter

        viewModelFactory = AcromineViewModelFactory(Provider.provideRepository(Provider.provideRemoteDataSource()))
        viewModel = ViewModelProvider(this, viewModelFactory).get(AcromineViewModel::class.java)

        searchButton.setOnClickListener {
            viewModel.getAcronyms(acronymEditext.text.toString()).observe(this, this)

            // hide keyboard after search click
            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    override fun onChanged(result: Result?) {
        result?.let {
            when(it) {
                is Result.Success -> {
                    val responseList = it.acromineList
                    if (responseList.isNullOrEmpty()) {
                        Toast.makeText(applicationContext, "No results for the acronym!!", Toast.LENGTH_LONG).show()
                    } else {
                        acronymsAdapter.setData(responseList[0].longForms)
                    }
                }

                is Result.Error -> {
                    Toast.makeText(applicationContext, it.exception.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}