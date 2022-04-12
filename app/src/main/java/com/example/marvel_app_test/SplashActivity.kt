package com.example.marvel_app_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startSplash()
        setupScreen()

    }

    private fun startSplash(){
        val retornoAsync = CoroutineScope(Dispatchers.Main).async {
            waitSplashScreenTimer()
        }
        CoroutineScope(Dispatchers.Main).launch {
            val result = retornoAsync.await()
            if (result)
                callNewActivity()
        }
    }

    private suspend fun waitSplashScreenTimer(): Boolean {
        delay(3000)
        return true
    }

    private fun callNewActivity() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    private fun setupScreen() {
        supportActionBar?.hide()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}