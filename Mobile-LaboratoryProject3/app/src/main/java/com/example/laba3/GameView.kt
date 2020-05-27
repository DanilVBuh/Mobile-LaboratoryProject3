package com.example.laba3

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.laba3.Classes.Ball
import com.example.laba3.Classes.Bonus
import com.example.laba3.Classes.Brick
import com.example.laba3.Classes.Player
import kotlin.math.abs


class GameView(context: Context, attributes: AttributeSet) : SurfaceView(context, attributes),
    SurfaceHolder.Callback {

    var infinityMod = true
    private val thread: GameThread
    private var balls: ArrayList<Ball> = ArrayList()
    private var bonuses: ArrayList<Bonus> = ArrayList()
    private var player: Player? = null
    private var bricks: ArrayList<Brick> = ArrayList()
    private val options = BitmapFactory.Options()
    var score = 0


    private var touched: Boolean = false
    private var touched_x: Int = 0
    private var touched_y: Int = 0
    private var column: Int = 14
    private var row: Int = 14
    private var r: Int = 20

    private var ballsCount: Int = 0
    private var bricksCount: Int = 0
    val table = Array(row) { Array(column) { 0 } }
    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels//1080
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels//2020

    init {
//        table[0] = arrayOf(0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0)
//        table[1] = arrayOf(0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0)
//        table[2] = arrayOf(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0)
//        table[3] = arrayOf(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0)
//        table[4] = arrayOf(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0)
//        table[5] = arrayOf(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0)
//        table[6] = arrayOf(0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0)
//        table[7] = arrayOf(0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0)
//        table[8] = arrayOf(0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0)
//        table[9] = arrayOf(0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0)
//        table[10] = arrayOf(0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0)
//        table[0] = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
//        table[1] = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
//        table[2] = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
//        table[3] = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
//        table[4] = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
//        table[5] = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
//        table[6] = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
//        table[7] = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
//        table[8] = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
//        table[9] = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
//        table[10] = arrayOf(0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0)
        for (i in 0 until row)
            for (j in 0 until column) {
                val d = (0..999).random()
                if (d < 80) {
                    table[i][j] = 3
                } else if (d < 250) {
                    table[i][j] = 2
                } else if (d < 600) {
                    table[i][j] = 0
                } else if (d < 1000) {
                    table[i][j] = 1
                }
            }
        options.inSampleSize = 2
        holder.addCallback(this)

        thread = GameThread(holder, this)
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        balls.add(
            Ball(
                BitmapFactory.decodeResource(
                    resources,
                    R.drawable.ball2,
                    options
                )
            )
        )
        player = Player(
            BitmapFactory.decodeResource(resources, R.drawable.paddle4, options)
        )

        for (i in 0 until row) {
            for (j in 0 until column) {
                if (table[i][j] == 1) {
                    val brickColor: Bitmap
                    val color: Int = (0..4).random()
                    brickColor = when (color) {
                        0 -> BitmapFactory.decodeResource(resources, R.drawable.brick7, options)
                        1 -> BitmapFactory.decodeResource(resources, R.drawable.brick3, options)
                        2 -> BitmapFactory.decodeResource(resources, R.drawable.brick4, options)
                        3 -> BitmapFactory.decodeResource(resources, R.drawable.brick5, options)
                        4 -> BitmapFactory.decodeResource(resources, R.drawable.brick6, options)
                        else -> BitmapFactory.decodeResource(resources, R.drawable.brick3, options)
                    }
                    bricks.add(Brick(brickColor, j, i, column, row, table[i][j]))
                }
                if (table[i][j] == 2) {
                    bricks.add(
                        Brick(
                            BitmapFactory.decodeResource(
                                resources,
                                R.drawable.brick8,
                                options
                            ), j, i, column, row, table[i][j]
                        )
                    )
                }
                if (table[i][j] == 3) {
                    bricks.add(
                        Brick(
                            BitmapFactory.decodeResource(
                                resources,
                                R.drawable.brick0,
                                options
                            ), j, i, column, row, table[i][j]
                        )
                    )
                }
            }
        }
        ballsCount = balls.size
        bricksCount = bricks.size
        // start the game thread
        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {

    }

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                thread.setRunning(false)
                thread.join()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            retry = false
        }
        //Toast.makeText(context, "YOU LOSE", Toast.LENGTH_SHORT).show()
    }

    fun update() {
        for (ball in balls) {
            if (ball.isVisible()) {
                val ballRect = Rect(ball.x, ball.y, ball.x + ball.w, ball.y + ball.h)
                val playerRect =
                    Rect(player!!.x, player!!.y, player!!.x + player!!.w, player!!.y + player!!.h)
                if (ballRect.intersect(playerRect)) {
                    if (minOf(
                            playerRect.bottom - ballRect.top,
                            ballRect.bottom - playerRect.top
                        ) < minOf(
                            playerRect.right - ballRect.left,
                            ballRect.right - playerRect.left
                        )
                    ) {
                        ball.gDeflect(
                            (ballRect.centerX().toFloat() - playerRect.centerX().toFloat()) / playerRect.width().toFloat() / (1 + (abs(
                                ballRect.centerX().toFloat() - playerRect.centerX().toFloat()
                            ) / playerRect.width().toFloat() / 2)), playerRect.top
                        )
                    } else {
                        ball.xDeflect()
                    }
                    if (ball.isCreazy()) {
                        ball.changeCreazy()
                        ball.image =
                            BitmapFactory.decodeResource(resources, R.drawable.ball2, options)
                    }
                }
                for (b in bricks) {
                    if (b.isVisible()) {
                        val brickRect = Rect(b.x, b.y, b.x + b.w, b.y + b.h)
                        if (ballRect.intersect(brickRect)) {
                            if (!ball.isCreazy()) {
                                if (minOf(
                                        brickRect.bottom - ballRect.top,
                                        ballRect.bottom - brickRect.top
                                    ) < minOf(
                                        brickRect.right - ballRect.left,
                                        ballRect.right - brickRect.left
                                    )
                                ) {
                                    ball.yDeflect()

                                } else {
                                    ball.xDeflect()
                                }
                            }
                            if (ball.isCreazy()) b.type = 1
                            if (b.breakBrick()) {
                                table[b.yPos][b.xPos] = 0
                                bricksCount--
                                val d = (0..399).random()
                                var type = 0
                                if (d < 15) {
                                    type = 0
                                } else if (d < 35) {
                                    type = 1
                                } else if (d < 55) {
                                    type = 2
                                } else if (d < 60) {
                                    type = 3
                                } else if (d < 64) {
                                    type = 4
                                } else if (d < 87) {
                                    type = 5
                                } else if (d < 100) {
                                    type = 6
                                } else {
                                    type = 20
                                }
                                when (type) {
                                    0 -> bonuses.add(
                                        Bonus(
                                            BitmapFactory.decodeResource(
                                                resources,
                                                R.drawable.bonus1,
                                                options
                                            ), brickRect.centerX(), brickRect.centerY(), type
                                        )
                                    )
                                    1 -> bonuses.add(
                                        Bonus(
                                            BitmapFactory.decodeResource(
                                                resources,
                                                R.drawable.bonus2,
                                                options
                                            ), brickRect.centerX(), brickRect.centerY(), type
                                        )
                                    )
                                    2 -> bonuses.add(
                                        Bonus(
                                            BitmapFactory.decodeResource(
                                                resources,
                                                R.drawable.bonus3,
                                                options
                                            ), brickRect.centerX(), brickRect.centerY(), type
                                        )
                                    )
                                    3 -> bonuses.add(
                                        Bonus(
                                            BitmapFactory.decodeResource(
                                                resources,
                                                R.drawable.bonus4,
                                                options
                                            ), brickRect.centerX(), brickRect.centerY(), type
                                        )
                                    )
                                    4 -> bonuses.add(
                                        Bonus(
                                            BitmapFactory.decodeResource(
                                                resources,
                                                R.drawable.bonus7,
                                                options
                                            ), brickRect.centerX(), brickRect.centerY(), type
                                        )
                                    )
                                    5 -> bonuses.add(
                                        Bonus(
                                            BitmapFactory.decodeResource(
                                                resources,
                                                R.drawable.bonus5,
                                                options
                                            ), brickRect.centerX(), brickRect.centerY(), type
                                        )
                                    )
                                    6 -> bonuses.add(
                                        Bonus(
                                            BitmapFactory.decodeResource(
                                                resources,
                                                R.drawable.bonus6,
                                                options
                                            ), brickRect.centerX(), brickRect.centerY(), type
                                        )
                                    )
                                }
                            } else if (b.type == 1) {
                                b.image = BitmapFactory.decodeResource(
                                    resources,
                                    R.drawable.brick9,
                                    options
                                )
                            }
                        }
                    }
                }
                if (ball.lose()) {
                    ball.setInVisible()
                    ballsCount--
                }
                ball.update()
            }
        }
        for (bonus in bonuses) {
            if (bonus.isVisible()) {
                val bonusRect = Rect(bonus.x, bonus.y, bonus.x + bonus.w, bonus.y + bonus.h)
                val playerRect =
                    Rect(player!!.x, player!!.y, player!!.x + player!!.w, player!!.y + player!!.h)
                if (bonusRect.intersect(playerRect)) {
                    bonusAct(bonus.type)
                    bonus.setInVisible()
                }
                bonus.update()
            }
        }
        if (touched && player!!.touchControl) {
            player!!.updateTouch(touched_x, touched_y)
        }
        if (infinityMod) {
            removeEmptyRows()
        }
    }


    override fun draw(canvas: Canvas) {
        var background = BitmapFactory.decodeResource(resources, R.drawable.space)
        background = Bitmap
            .createScaledBitmap(background, screenWidth, screenHeight, false)
        super.draw(canvas)
        val paint = Paint()
        paint.color = Color.WHITE
        if (bricksCount == 0) {
            //canvas.drawColor(Color.GREEN)
            canvas.drawBitmap(background, 0F, 0F, paint)
            paint.textSize = 150F
            canvas.drawText("YOU WIN", 100F, 1010F, paint)
        } else
            if (ballsCount == 0) {
                //canvas.drawColor(Color.RED)
                canvas.drawBitmap(background, 0F, 0F, paint)
                paint.textSize = 150F
                canvas.drawText("YOU LOSE", 100F, 800F, paint)
                paint.textSize = 100F
                canvas.drawText("YOUR SCORE: $score", 100F, 1100F, paint)
                paint.textSize = 70F
                canvas.drawText("YOUR BEST: "+PreferencesHandler(context).readString(), 100F, 1200F, paint)
                if (score > PreferencesHandler(context).readString().toInt())
                    PreferencesHandler(context).saveString(score.toString())
            } else {
                canvas.drawBitmap(background, 0F, 0F, paint)
                //canvas.drawColor(Color.LTGRAY)
                //paint.color = Color.BLACK
                //paint.textSize = 40F
                //val x=BitmapFactory.decodeResource(resources,R.drawable.brick3,options).width
                //val y =BitmapFactory.decodeResource(resources,R.drawable.brick3,options).height
                //canvas.drawText("${ball!!.xVelocity} ${ball!!.yVelocity}", 540F, 1510F, paint)
                paint.textSize = 40F
                score = bricks.size - bricksCount
                canvas.drawText("SCORE: $score", 20F, 40F, paint)
                for (b in bricks) {
                    if (b.isVisible())
                        b.draw(canvas)
                }
                for (b in bonuses) {
                    if (b.isVisible())
                        b.draw(canvas)
                }
                for (ball in balls) {
                    if (ball.isVisible())
                        ball.draw(canvas)
                }
                player!!.draw(canvas)
            }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        touched_x = event.x.toInt()
        touched_y = event.y.toInt()

        val action = event.action
        when (action) {
            MotionEvent.ACTION_DOWN -> touched = true
            MotionEvent.ACTION_MOVE -> touched = true
            MotionEvent.ACTION_UP -> touched = false
            MotionEvent.ACTION_CANCEL -> touched = false
            MotionEvent.ACTION_OUTSIDE -> touched = false
        }
        return true
    }

    fun updatePaddle(fl: Float, f2: Float) {
        if (player != null)
            player!!.updateMe(fl, f2)
    }

    fun getPaddleMod(): Boolean {
        if (player != null)
            return player!!.touchControl
        return true
    }

    fun pause() {
        thread.paused = !thread.paused
    }

    private fun bonusAct(type: Int) {
        when (type) {
            0 -> {
                balls.add(
                    Ball(
                        BitmapFactory.decodeResource(
                            resources,
                            R.drawable.ball2,
                            options
                        )
                    )
                )
                ballsCount++
            }
            1 -> {
                when (player!!.level) {
                    0 -> player!!.image =
                        BitmapFactory.decodeResource(resources, R.drawable.paddle7, options)
                    1 -> player!!.image =
                        BitmapFactory.decodeResource(resources, R.drawable.paddle3, options)
                    2 -> player!!.image =
                        BitmapFactory.decodeResource(resources, R.drawable.paddle5, options)
                    3 -> player!!.image =
                        BitmapFactory.decodeResource(resources, R.drawable.paddle4, options)
                    4 -> player!!.image =
                        BitmapFactory.decodeResource(resources, R.drawable.paddle8, options)
                }
                player!!.levelUp()
                player!!.resize()
            }
            2 -> {
                when (player!!.level) {
                    1 -> player!!.image =
                        BitmapFactory.decodeResource(resources, R.drawable.paddle6, options)
                    2 -> player!!.image =
                        BitmapFactory.decodeResource(resources, R.drawable.paddle7, options)
                    3 -> player!!.image =
                        BitmapFactory.decodeResource(resources, R.drawable.paddle3, options)
                    4 -> player!!.image =
                        BitmapFactory.decodeResource(resources, R.drawable.paddle5, options)
                    5 -> player!!.image =
                        BitmapFactory.decodeResource(resources, R.drawable.paddle4, options)
                }
                player!!.levelDown()
                player!!.resize()
            }
            3 -> {
                for (b in balls) {
                    if (b.isVisible()) {
                        b.changeCreazy()
                        b.image = BitmapFactory.decodeResource(resources, R.drawable.ball3, options)
                    }
                }
            }
            4 -> {
                val ballsN: ArrayList<Ball> = ArrayList()
                for (b in balls) {
                    var ball =
                        Ball(BitmapFactory.decodeResource(resources, R.drawable.ball2, options))
                    ball.x = b.x
                    ball.y = b.y
                    ballsN.add(ball)
                    ball =
                        Ball(BitmapFactory.decodeResource(resources, R.drawable.ball2, options))
                    ball.x = b.x
                    ball.y = b.y
                    ballsN.add(ball)
                    ballsCount += 2
                }
                for (b in ballsN) {
                    balls.add(b)
                }
            }
            5 -> {
                for (b in balls) {
                    if (b.isVisible())
                        b.velocityUp()
                }
            }
            6 -> {
                for (b in balls) {
                    if (b.isVisible())
                        b.velocityDown()
                }
            }
            7 -> {

            }
        }
    }

    fun removeEmptyRows() {
        for (i in 0 until row) {
            var k = 0
            for (j in 0 until column) {
                if (table[i][j] == 0 || table[i][j] == 3) {
                    k++
                }
            }
            if (k == column) {
                for (m in i downTo 1) {
                    table[m] = table[m - 1]
                    for (b in bricks) {
                        if (b.isVisible() && b.yPos == m - 1) {
                            b.move()
                        }
                    }
                }
                for (t in 0 until column) {
                    val d = (0..999).random()
                    if (d < 80) {
                        table[0][t] = 3
                    } else if (d < 250) {
                        table[0][t] = 2
                    } else if (d < 600) {
                        table[0][t] = 0
                    } else if (d < 1000) {
                        table[0][t] = 1
                    }
                    if (table[0][t] == 1) {
                        val brickColor: Bitmap
                        val color: Int = (0..4).random()
                        brickColor = when (color) {
                            0 -> BitmapFactory.decodeResource(resources, R.drawable.brick7, options)
                            1 -> BitmapFactory.decodeResource(resources, R.drawable.brick3, options)
                            2 -> BitmapFactory.decodeResource(resources, R.drawable.brick4, options)
                            3 -> BitmapFactory.decodeResource(resources, R.drawable.brick5, options)
                            4 -> BitmapFactory.decodeResource(resources, R.drawable.brick6, options)
                            else -> BitmapFactory.decodeResource(
                                resources,
                                R.drawable.brick3,
                                options
                            )
                        }
                        bricks.add(Brick(brickColor, t, 0, column, row, table[0][t]))
                    }
                    if (table[0][t] == 2) {
                        bricks.add(
                            Brick(
                                BitmapFactory.decodeResource(
                                    resources,
                                    R.drawable.brick8,
                                    options
                                ), t, 0, column, row, table[0][t]
                            )
                        )
                    }
                    if (table[0][t] == 3) {
                        bricks.add(
                            Brick(
                                BitmapFactory.decodeResource(
                                    resources,
                                    R.drawable.brick0,
                                    options
                                ), t, 0, column, row, table[0][t]
                            )
                        )
                    }
                }
            }
        }
    }

    fun changePaddleMod() {
        player!!.touchControl = !player!!.touchControl
    }
}