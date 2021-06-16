package com.yuchiaoc.githubapidemo.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.yuchiaoc.githubapidemo.model.User
import com.yuchiaoc.githubapidemo.network.RestApi
import com.yuchiaoc.githubapidemo.network.State
import retrofit2.Call
import retrofit2.Response

class UserDataSource(
    private val searchName: String
) : PageKeyedDataSource<Int, User>() {

    private val restApi = RestApi.create()
    private val initialLoad = MutableLiveData<State>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, User>
    ) {
        val request = restApi.fetch(
            q = "$searchName+in:name",
            page = 1,
            pageSize = params.requestedLoadSize
        )
        initialLoad.postValue(State.LOADING)

        val response = request.execute()
//        val link = response.headers().get("link")
        val data = response.body()
        initialLoad.postValue(State.DONE)
        val users = data?.users ?: emptyList()
        callback.onResult(users, null, 2)

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        restApi.fetch(
            q = "$searchName+in:name",
            page = params.key,
            pageSize = params.requestedLoadSize
        ).enqueue(
            object : retrofit2.Callback<RestApi.Response> {
                override fun onFailure(call: Call<RestApi.Response>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<RestApi.Response>,
                    response: Response<RestApi.Response>
                ) {
                    if (response.isSuccessful) {
                        val lastPage = (1000 / params.requestedLoadSize) + 1
                        val nextKey = if (params.key == lastPage) null else {
                            params.key + 1
                        }
//                        val link = response.headers().get("link")
                        val data = response.body()
                        val users = data?.users ?: emptyList()
                        callback.onResult(users, nextKey)
                    }
                }
            }
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {

    }

}