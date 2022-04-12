package com.example.marvel_app_test.utils

import androidx.fragment.app.Fragment

interface Navigation {

    fun navigateTo(fragment: Fragment, addToBackstack: Boolean = true, fragmentToHide: Fragment? = null)
}