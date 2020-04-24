package com.example.interactiveanimation.ui.main

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import com.example.interactiveanimation.R
import kotlin.math.max
import kotlin.math.min

private const val ANIMATION_DURATION = 500L
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

    private fun calculateSliderHeight(eventY: Float) {
        val deltaY = (previousY - eventY)
        sliderHeight += deltaY.toInt()
        sliderHeight = min(sliderHeight, sliderMaxHeight)
        sliderHeight = max(sliderHeight, sliderMinHeight)
    }

    private fun slideWithAnimation() {
        val toHeight = when {
            sliderHeight == sliderMinHeight -> sliderMaxHeight
            sliderHeight == sliderMaxHeight -> sliderMinHeight
            sliderMaxHeight - sliderHeight < sliderHeight - sliderMinHeight -> sliderMaxHeight
            else -> sliderMinHeight
        }
        val slideAnimator = ValueAnimator
            .ofInt(sliderHeight, toHeight)
            .setDuration(ANIMATION_DURATION)
            .also {
                it.addUpdateListener {
                    sliderHeight = it.animatedValue as Int
                    requestLayout()
                }
            }

        AnimatorSet().also {
            it.interpolator = AccelerateDecelerateInterpolator()
            it.play(slideAnimator)
            it.start()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                previousY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                calculateSliderHeight(eventY = event.y)
                requestLayout()
            }
            MotionEvent.ACTION_UP -> {
                slideWithAnimation()
            }
        }
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            widthMeasureSpec,
            MeasureSpec.makeMeasureSpec(sliderHeight, MeasureSpec.getMode(heightMeasureSpec))
        )
    }
}