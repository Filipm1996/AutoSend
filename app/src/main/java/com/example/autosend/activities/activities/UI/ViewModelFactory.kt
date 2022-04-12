package com.example.autosend.activities.activities.UI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.autosend.activities.activities.repositories.Repository

class ViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AutoSendViewModel(repository) as T
    }
}