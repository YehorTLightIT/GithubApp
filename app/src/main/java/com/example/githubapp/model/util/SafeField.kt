package com.example.githubapp.model.util

class SafeField <T: Any>(private var value: T) {
    private var isHandled = true
    fun get(action: (T) -> Unit){
        if(!isHandled){
            isHandled = true
            action(value)
        }
    }
    fun set(value: T){
        this.value = value
        isHandled = false
    }
}