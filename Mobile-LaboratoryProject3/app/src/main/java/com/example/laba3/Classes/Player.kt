package com.example.laba3.Classes

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas

class Player(var image: Bitmap) {
    var x: Int = 0
    var y: Int = 0
    var w: Int
    var h: Int
    var xVelocity = 30
    var level: Int = 3
    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels
    var touchControl: Boolean = false

    var xNew: Int = 0

    // ball coordinates
    var cx: Float
    var cy: Float

    // last position increment

    var lastGx: Float = 0.toFloat()
    var lastGy: Float = 0.toFloat()

    init {
        w = image.width
        h = image.height

        x = screenWidth / 2 - w / 2
        y = screenHeight - 400

        cx = x.toFloat()
        cy = y.toFloat()

    }


    fun draw(canvas: Canvas) {
        canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)
    }


    fun updateTouch(touch_x: Int, touch_y: Int) {

        xNew = touch_x - w / 2
        if (touch_x < w / 2) {
            xNew = 0
        }
        if (touch_x > screenWidth - w / 2) {
            xNew = screenWidth - w
        }
        when {
            x > xNew + 3 * xVelocity -> x -= xVelocity
            x < xNew - 3 * xVelocity -> x += xVelocity
            else -> {
                x = xNew
            }
        }
        //y = touch_y - h / 2
    }

    fun updateMe(inx: Float, iny: Float) {
        lastGx += inx * 10
        //lastGy += iny

        cx += inx * 3F
        //cx += lastGx
        //cy += lastGy

        if (cx > (screenWidth - w)) {
            cx = (screenWidth - w).toFloat()
            lastGx = 0F
        } else if (cx < (0)) {
            cx = 0F
            lastGx = 0F

        }
        x = cx.toInt()
    }

    fun levelUp() {
        if (level < 5)
            level++
    }

    fun levelDown() {
        if (level > 0)
            level--
    }

    fun resize() {
        val wOld = w
        w = image.width
        x = x + wOld / 2 - w / 2

    }

}