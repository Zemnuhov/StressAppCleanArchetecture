package com.neurotech.stressapp.ui.customview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.neurotech.stressapp.R
import kotlin.math.min

class WaitingTextView(context: Context, attributeSet: AttributeSet?):
    View(context,attributeSet){

    private var grad  =  LinearGradient(0f, 0f, 1000f, 100F,
        Color.WHITE,
        ContextCompat.getColor(context, R.color.background_color),
        Shader.TileMode.MIRROR)

    private val paint get() =  Paint().apply {
        color = ContextCompat.getColor(context, R.color.download_background)
        shader = grad
    }

    private val rectF = RectF()

    fun startAnimation(){
        ValueAnimator.ofFloat(0F, 10000F).apply {
            duration = 15000L
            addUpdateListener {
                grad = LinearGradient(it.animatedValue as Float,
                    it.animatedValue as Float,
                    it.animatedValue as Float+300,
                    it.animatedValue as Float+100,
                    Color.WHITE,
                    ContextCompat.getColor(context, R.color.download_background),
                    Shader.TileMode.MIRROR)
                invalidate()
            }
            start()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = 150
        val desiredHeight = 150

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

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        rectF.set(0F,0F, width.toFloat(),height.toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(rectF, 100F,100F, paint)
    }
}