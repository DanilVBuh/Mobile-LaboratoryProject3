package com.example.laba3.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import com.example.laba3.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            // start your next activity
            startActivity(intent)
        }
    }
}
