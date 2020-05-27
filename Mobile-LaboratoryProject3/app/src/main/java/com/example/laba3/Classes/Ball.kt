package com.example.laba3.Classes

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import kotlin.math.*

class Ball(var image: Bitmap) {
    var x: Int = 0
    var y: Int = 0
    var w: Int = 0
    var h: Int = 0
    var xVelocity = 1500
    var yVelocity = -1500
    var islose = false
    private var visible: Boolean = true
    var creazy = false


    private var velocity: Float
    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels//1080
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels//2020

    init {
        w = image.width
        h = image.height
        velocity = sqrt((xVelocity * xVelocity + yVelocity * yVelocity).toFloat())

        xVelocity = (-2000..2000).random()
        yVelocity = -sqrt(velocity * velocity - xVelocity * xVelocity).toInt()
        x = screenWidth / 2
        y = screenHeight / 4 * 3
    }

    /**
     * Draws the object on to the canvas.
     */
    fun draw(canvas: Canvas) {
        canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)
    }


    fun update() {
        // val randomNum = ThreadLocalRandom.current().nextInt(1, 5)
        if (x > screenWidth - image.width || x < 0) {
            xVelocity *= -1
        }
        if (y > screenHeight - image.height || y < 0) {
            yVelocity *= -1
        }
        if (x < -abs(xVelocity)) {
            x = 0
        }
        if (x > screenWidth - image.width + abs(xVelocity)) {
            x = screenWidth - image.width
        }
        if (y < -abs(yVelocity)) {
            y = 0
        }
        if (y > screenHeight - image.height + abs(yVelocity)) {
            y = screenHeight - image.height
        }
        x += (xVelocity) / 100
        //yVelocity += yAcceleration
        y += (yVelocity) / 100

    }

    fun deflect() {
        xVelocity *= -1
        yVelocity *= -1
    }

    fun lose(): Boolean {
        if (y > screenHeight - 300) {
            islose = true
        }
        return islose
        //return false
    }

    fun gDeflect(tgAlf: Float, top: Int) {
        val alf: Float = atan(tgAlf)
        val bet: Float = atan(xVelocity.toFloat() / yVelocity.toFloat())
        xVelocity = (velocity * sin(2 * alf + bet)).toInt()
        yVelocity = -(velocity * abs(cos(2 * alf + bet))).toInt()
        if (yVelocity / 100 == 0) {
            yVelocity = -200
            xVelocity =
                ((xVelocity / abs(xVelocity)) * sqrt((xVelocity * xVelocity + yVelocity * yVelocity).toFloat())).toInt()
        }
        y = top - h
    }

    fun xDeflect() {
        //yVelocity += yAcceleration
        xVelocity *= -1
    }

    fun yDeflect() {
        //yVelocity += yAcceleration
        yVelocity *= -1
    }

    fun isVisible(): Boolean {
        return visible
    }

    fun isCreazy(): Boolean {
        return creazy
    }

    fun changeCreazy(){
        creazy=!creazy
    }

    fun velocityUp(){
        velocity*=1.3F
    }
    fun velocityDown(){
        velocity/=1.3F
    }

    fun setInVisible() {
        visible = false
    }

}