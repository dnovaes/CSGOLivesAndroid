package com.dnovaes.csgolive.matches.common.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dnovaes.csgolive.databinding.ActivityMatchesBinding

class MatchesActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMatchesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.txtViewMain.text = MatchesActivity::class.java.simpleName
    }

}