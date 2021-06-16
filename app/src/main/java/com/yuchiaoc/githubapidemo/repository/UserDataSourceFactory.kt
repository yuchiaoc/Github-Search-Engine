package com.yuchiaoc.githubapidemo.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.yuchiaoc.githubapidemo.model.User

class UserDataSourceFactory(
    private val searchName: String
) : DataSource.Factory<Int, User>() {
    private val userDataSourceLiveData = MutableLiveData<UserDataSource>()
    override fun create(): DataSource<Int, User> {
        val source = UserDataSource(searchName)
        userDataSourceLiveData.postValue(source)
        return source
    }

}