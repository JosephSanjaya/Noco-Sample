package com.example.myapplication.model

data class State<T>(
    val isLoading: Boolean = true,
    val isComplete: Boolean = false,
    val data: T? = null,
    val isError: Boolean = false,
    val throwable: Throwable? = null
)
