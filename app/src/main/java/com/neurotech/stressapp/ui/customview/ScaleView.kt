package com.neurotech.stressapp.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.neurotech.stressapp.R
import kotlin.math.min

class ScaleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val greenRectOne = RectF()
    private val greenRectTwo = RectF()
    private val greenRectThree = RectF()

    private val yellowRectOne = RectF()
    private val yellowRectTwo = RectF()
    private val yellowRectThree = RectF()

    private val redRectOne = RectF()
    private val redRectTwo = RectF()
    private val redRectThree = RectF()

    private val margin = 40

    public var value = 0
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawGreenScale(canvas)
        drawYellowScale(canvas)
        drawRedScale(canvas)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = 150
        val desiredHeight = 350

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width: Int = when (widthMode) {
            MeasureSpec.EXACTLY -> {
                widthSize
            }
            MeasureSpec.AT_MOST -> {
                min(desiredWidth, widthSize)
            }
            else -> {
                desiredWidth
            }
        }

        val height: Int = when (heightMode) {
            MeasureSpec.EXACTLY -> {
                heightSize
            }
            MeasureSpec.AT_MOST -> {
                min(desiredHeight, heightSize)
            }
            else -> {
                desiredHeight
            }
        }

        setMeasuredDimension(width, height)
    }

    private fun drawGreenScale(canvas: Canvas){
        paint.color = when(value){
            in 1000..10000 -> ContextCompat.getColor(context, R.color.green_active)
            else -> ContextCompat.getColor(context, R.color.green_not_active)
        }
        greenRectOne.set(
            (width-40).toFloat(),
            (height/2+150).toFloat(),
            (width/2+40).toFloat(),
            (height/2+175).toFloat()
        )
        canvas.drawRect(greenRectOne,paint)

        paint.color = when(value){
            in 2000..10000 -> ContextCompat.getColor(context, R.color.green_active)
            else -> ContextCompat.getColor(context, R.color.green_not_active)
        }

        greenRectTwo.set(
            greenRectOne.left,
            greenRectOne.top-margin,
            greenRectOne.right,
            greenRectOne.bottom-margin
        )
        canvas.drawRect(greenRectTwo,paint)

        paint.color = when(value){
            in 3000..10000 -> ContextCompat.getColor(context, R.color.green_active)
            else -> ContextCompat.getColor(context, R.color.green_not_active)
        }

        greenRectThree.set(
            greenRectTwo.left,
            greenRectTwo.top-margin,
            greenRectTwo.right,
            greenRectTwo.bottom-margin
        )
        canvas.drawRect(greenRectThree,paint)
    }

    private fun drawYellowScale(canvas: Canvas){
        paint.color = when(value){
            in 4000..10000 -> ContextCompat.getColor(context, R.color.yellow_active)
            else -> ContextCompat.getColor(context, R.color.yellow_not_active)
        }

        yellowRectOne.set(
            greenRectThree.left-5,
            greenRectThree.top-margin,
            greenRectThree.right+5,
            greenRectThree.bottom-margin
        )
        canvas.drawRect(yellowRectOne,paint)

        paint.color = when(value){
            in 5000..10000 -> ContextCompat.getColor(context, R.color.yellow_active)
            else -> ContextCompat.getColor(context, R.color.yellow_not_active)
        }

        yellowRectTwo.set(
            yellowRectOne.left-5,
            yellowRectOne.top-margin,
            yellowRectOne.right+5,
            yellowRectOne.bottom-margin
        )
        canvas.drawRect(yellowRectTwo,paint)

        paint.color = when(value){
            in 6000..10000 -> ContextCompat.getColor(context, R.color.yellow_active)
            else -> ContextCompat.getColor(context, R.color.yellow_not_active)
        }
        yellowRectThree.set(
            yellowRectTwo.left-5,
            yellowRectTwo.top-margin,
            yellowRectTwo.right+5,
            yellowRectTwo.bottom-margin
        )
        canvas.drawRect(yellowRectThree,paint)
    }

    private fun drawRedScale(canvas: Canvas){
        paint.color = when(value){
            in 7000..10000 -> ContextCompat.getColor(context, R.color.red_active)
            else -> ContextCompat.getColor(context, R.color.red_not_active)
        }

        redRectOne.set(
            yellowRectThree.left-5,
            yellowRectThree.top-margin,
            yellowRectThree.right+5,
            yellowRectThree.bottom-margin
        )
        canvas.drawRect(redRectOne,paint)

        paint.color = when(value){
            in 8000..10000 -> ContextCompat.getColor(context, R.color.red_active)
            else -> ContextCompat.getColor(context, R.color.red_not_active)
        }

        redRectTwo.set(
            redRectOne.left-5,
            redRectOne.top-margin,
            redRectOne.right+5,
            redRectOne.bottom-margin
        )
        canvas.drawRect(redRectTwo,paint)

        paint.color = when(value){
            in 9000..10000 -> ContextCompat.getColor(context, R.color.red_active)
            else -> ContextCompat.getColor(context, R.color.red_not_active)
        }

        redRectThree.set(
            redRectTwo.left-5,
            redRectTwo.top-margin,
            redRectTwo.right+5,
            redRectTwo.bottom-margin
        )
        canvas.drawRect(redRectThree,paint)
    }
}