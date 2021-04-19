package com.example.embracecrash.utils

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified VM : ViewModel> FragmentActivity.viewModelProvider(
    provider: ViewModelProvider.Factory
) = ViewModelProvider(this, provider).get(VM::class.java)

