package com.example.interactiveanimation.ui.main

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import androidx.annotation.FloatRange
import com.example.interactiveanimation.R
import kotlin.math.max
import kotlin.math.min

private const val ANIMATION_DURATION = 500L

/**
 * This layout used to render the sliding views. This layout extends [FrameLayout] and update the
 * layout height according to user's touch events.
 */
class SlideLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    /**
     *  The minimum height of the slider
     */
    private var sliderMinHeight = 0

    /**
     * The maximum height of the slider
     */
    private var sliderMaxHeight = 0

    /**
     * The actual height of the slider
     */
    private var sliderHeight = 0

    /**
     * The starting y position of the motion event
     */
    private var initialPosY = 0F

    /**
     * velocity of the slider during interactive mode. The velocity of the slider should be
     *  in range 0 to 1.
     */
    private var sliderVelocity = 1F

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlideLayout)
        sliderMaxHeight =
            typedArray.getDimensionPixelSize(R.styleable.SlideLayout_sliderMaxHeight, 0)
        sliderMinHeight =
            typedArray.getDimensionPixelSize(R.styleable.SlideLayout_sliderMinHeight, 0)
        typedArray.recycle()
        sliderHeight = sliderMinHeight
    }

    /**
     * method used to calculate the slider height
     * @param eventY the y position of the touch event
     */
    private fun calculateSliderHeight(eventY: Float) {
        val deltaY = (initialPosY - eventY) * sliderVelocity
        sliderHeight += deltaY.toInt()
        sliderHeight = min(sliderHeight, sliderMaxHeight)
        sliderHeight = max(sliderHeight, sliderMinHeight)
    }

    /**
     * method used to slide the layout with animation
     */
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

    /**
     * method used to update the slider velocity during interactive mode
     * @param velocity the velocity of the slider, should be in range 0 to 1.
     */
    fun setSliderVelocity(@FloatRange(from = 0.0, to = 1.0) velocity: Float) {
        sliderVelocity = velocity
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                initialPosY = event.y
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