package com.example.marvel_app_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.marvel_app_test.utils.Navigation
import com.example.marvel_app_test.view.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Navigation {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        navigateTo(MainFragment(this), false)
    }

    override fun navigateTo(fragment: Fragment, addToBackstack: Boolean, fragmentToHide: Fragment?) {
        val transaction = supportFragmentManager.beginTransaction()
        if (addToBackstack) {
            transaction.addToBackStack(null)
        }
        if (fragmentToHide != null){
            transaction.hide(fragmentToHide)
                .add(R.id.container, fragment)
                .commit()
        }else{
            transaction
                .replace(R.id.container, fragment)
                .commit()
        }
    }

}