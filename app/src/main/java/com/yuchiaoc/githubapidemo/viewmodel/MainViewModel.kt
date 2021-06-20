package com.yuchiaoc.githubapidemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.yuchiaoc.githubapidemo.model.User
import com.yuchiaoc.githubapidemo.repository.UserRepository

class MainViewModel : ViewModel() {
    private lateinit var repo: UserRepository

    fun getUsers(): LiveData<PagedList<User>> {
        return repo.getUserListLiveData()
    }

    fun search(searchName: String) {
        repo = UserRepository(searchName)
    }
}