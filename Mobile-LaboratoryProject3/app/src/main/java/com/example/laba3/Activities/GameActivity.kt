package com.example.laba3.Activities

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.WindowManager
import com.example.laba3.PreferencesHandler
import com.example.laba3.R
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_main.*


class GameActivity : Activity(), SensorEventListener {
    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // remove notification bar
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        var pause = false
        var touch = false
        // get reference of the service
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        // focus in accelerometer
        mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        setContentView(com.example.laba3.R.layout.activity_game)

        swapButton.setOnClickListener {
            gameView.changePaddleMod()
            if (touch) {
                swapButton.setImageResource(R.drawable.ic_touch_app_black_24dp)

            } else {
                swapButton.setImageResource(R.drawable.ic_screen_rotation_black_24dp)
            }
            touch = !touch
        }

        pauseButton.setOnClickListener {
            gameView.pause()
            if (pause) {
                pauseButton.setImageResource(R.drawable.ic_pause_black_24dp)

            } else {
                pauseButton.setImageResource(R.drawable.ic_play_arrow_black_24dp)
            }
            pause = !pause
        }
        //if (scoreText != null)
        //var t=PreferencesHandler(applicationContext).readString()
            //scoreText.text = t
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null && !gameView.getPaddleMod()) {
            gameView!!.updatePaddle(-event.values[0], event.values[0])
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(
            this, mAccelerometer,
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(this)
    }
}
