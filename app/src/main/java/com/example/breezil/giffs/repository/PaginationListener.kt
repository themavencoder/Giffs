package com.example.breezil.giffs.repository

import androidx.paging.PageKeyedDataSource

interface PaginationListener<Response, Result> {

    fun onInitialError(throwable: Throwable)

    fun onInitialSuccess(
        response: Response,
        callback: PageKeyedDataSource.LoadInitialCallback<Int, Result>,
        results: MutableList<Result>
    )

    fun onPaginationError(throwable: Throwable)

    fun onPaginationSuccess(
        response: Response,
        callback: PageKeyedDataSource.LoadCallback<Int, Result>,
        params: PageKeyedDataSource.LoadParams<Int>,
        results: MutableList<Result>
    )

    fun clear()
}