package com.example.interactiveanimation.ui.main

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import com.example.interactiveanimation.R
import kotlin.math.min
import kotlin.math.max

class SlideLayout : FrameLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlideLayout)
        sliderMaxHeight =
            typedArray.getDimensionPixelSize(R.styleable.SlideLayout_sliderMaxHeight, 0)
        sliderMinHeight =
            typedArray.getDimensionPixelSize(R.styleable.SlideLayout_sliderMinHeight, 0)
        typedArray.recycle()
        sliderHeight = sliderMinHeight
    }

    private var sliderMinHeight = 0
    private var sliderMaxHeight = 0
    private var sliderHeight = 0
    private var previousY = 0F
    private var deltaY = 0F

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                previousY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                deltaY = (previousY- event.y)
                previousY = event.y
                requestLayout()
            }
        }
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            sliderHeight += deltaY.toInt()
            sliderHeight = min(sliderHeight, sliderMaxHeight)
            sliderHeight = max(sliderHeight, sliderMinHeight)
        val heightMeasureSpec =
            MeasureSpec.makeMeasureSpec(sliderHeight, MeasureSpec.getMode(heightMeasureSpec))
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}