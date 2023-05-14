package com.dnovaes.csgolive.launcher

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import com.dnovaes.csgolive.databinding.ActivityMainBinding
import com.dnovaes.csgolive.matches.common.view.MatchesActivity

class LauncherActivity: ComponentActivity() {

    private val handler = Handler(Looper.getMainLooper())

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.txtViewMain.text = this@LauncherActivity::class.java.simpleName
    }

    override fun onStart() {
        super.onStart()
        handler.postDelayed({
            val intent = Intent(
                this@LauncherActivity,
                MatchesActivity::class.java
            )
            startActivity(intent)
            finish()
        }, 3000)
    }

    override fun onStop() {
        handler.removeCallbacksAndMessages(null)
        super.onStop()
    }
}