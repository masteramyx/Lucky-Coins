package com.paperspace.kyleamyx

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.paperspace.kyleamyx.luckycoins.CoinMainActivity
import com.paperspace.kyleamyx.luckycoins.R

class SplashActivity : Activity() {

    private val splashTime = 2000L
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //hide notification bar for solid background on splash screen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        handler = Handler()

        handler.postDelayed(Runnable {
            startMainActivity()
        }, splashTime)

    }

    fun startMainActivity() {
        val mainActivityIntent = Intent(applicationContext, CoinMainActivity::class.java)
        startActivity(mainActivityIntent)
        finish()
    }
}

