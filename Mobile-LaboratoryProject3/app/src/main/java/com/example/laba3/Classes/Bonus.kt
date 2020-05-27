package com.example.laba3.Classes

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.View
import com.example.laba3.R
import kotlin.math.abs
import kotlin.math.sqrt

class Bonus (var image: Bitmap, xCen: Int, yCen: Int, typeNew: Int){
    var type: Int = typeNew
    var x: Int = 0
    var y: Int = 0
    var w: Int = 0
    var h: Int = 0
    var yVelocity = 9
    private var visible: Boolean = true
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    init {
        w = image.width
        h = image.height
        x = xCen - w/2
        y = yCen - h/2
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)
    }

    fun update() {
        if (y > screenHeight ) {
            visible=false
        }
        y += (yVelocity)

    }

    fun isVisible(): Boolean {
        return visible
    }

    fun setInVisible() {
        visible = false
    }

}