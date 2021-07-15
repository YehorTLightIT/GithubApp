package com.example.githubapp.model.util

class LiveDataEvent<T: Any>(private val value: T) {
    private var isHandled = false

    fun get(action: (T) -> Unit){
        if(!isHandled){
            isHandled = true
            action(value)
        }
    }
}