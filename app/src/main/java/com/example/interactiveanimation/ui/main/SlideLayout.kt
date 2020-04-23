package com.example.interactiveanimation.ui.main

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout

class SlideLayout : FrameLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    private var width1 = 0
    private var shouldUpdate = true
    private var previousY = 0F
    private var increase = false

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_MOVE -> {
                increase = event.y > previousY
                previousY = event.y
                Log.d("***", "move" + event.y)
                requestLayout()
            }
            MotionEvent.ACTION_UP -> {

                Log.d("***", "up")
            }
            MotionEvent.ACTION_DOWN -> {
                Log.d("***", "down")
            }
        }
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (shouldUpdate) {
            width1 = MeasureSpec.getSize(heightMeasureSpec)
            shouldUpdate = false
        }
        if (increase) {
            width1 += 50
        } else {
            width1 -= 50
        }
        val heightMeasureSpec =
            MeasureSpec.makeMeasureSpec(width1, MeasureSpec.getMode(heightMeasureSpec))
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}