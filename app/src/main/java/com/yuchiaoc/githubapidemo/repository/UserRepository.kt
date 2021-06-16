package com.yuchiaoc.githubapidemo.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.yuchiaoc.githubapidemo.model.User

class UserRepository(searchName: String) {
    private val pageSize = 10
    private val userDataSourceFactory: UserDataSourceFactory =
        UserDataSourceFactory(searchName)
    private var userList: LiveData<PagedList<User>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        userList = LivePagedListBuilder<Int, User>(userDataSourceFactory, config).build()
    }

    fun getUserListLiveData(): LiveData<PagedList<User>> {
        return userList
    }
}