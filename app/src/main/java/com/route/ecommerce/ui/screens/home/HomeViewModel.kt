package com.route.ecommerce.ui.screens.home

import androidx.lifecycle.ViewModel
import com.route.data.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel()
