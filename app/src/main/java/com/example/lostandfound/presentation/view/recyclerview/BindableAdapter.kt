package com.example.lostandfound.presentation.view.recyclerview

interface BindableAdapter<T> {
    fun update(data: T?)
}