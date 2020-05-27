package com.example.laba3.Classes

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas

class Brick(
    var image: Bitmap,
    var xPos: Int,
    var yPos: Int,
    private var column: Int,
    private var row: Int,
    var type: Int
) {
    var x: Int = 0
    var y: Int = 0
    val w: Int
    val h: Int

    private var visible: Boolean = true

    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    init {
        w = image.width
        h = image.height

        x = screenWidth / 2 - w * column / 2 + w * xPos
        y = 2 * h + h * yPos
    }

    /**
     * Draws the object on to the canvas.
     */
    fun draw(canvas: Canvas) {
        canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)
    }

    fun isVisible(): Boolean {
        return visible
    }

    fun breakBrick(): Boolean {
        when (type) {
            1 -> {
                visible = false
                return true
            }
            2 -> {
                type--
            }
        }
        return false
    }

    fun move(){
        yPos++
        x = screenWidth / 2 - w * column / 2 + w * xPos
        y = 2 * h + h * yPos
    }
}