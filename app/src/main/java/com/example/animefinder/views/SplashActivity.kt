package com.example.animefinder.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.animefinder.R
import com.example.animefinder.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val mHandler by lazy { Handler(Looper.getMainLooper()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        this.installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setBackgroundDrawable(getColor(R.color.color_primary).toDrawable())
        mHandler.postDelayed({
            startActivity(Intent(this, AnimeListActivity::class.java))
            finish()
        }, 3000)
    }

    override fun onDestroy() {
    mHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}