package com.example.interactiveanimation.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.TextViewCompat
import com.example.interactiveanimation.R
import kotlinx.android.synthetic.main.main_fragment.*

/**
 * Fragment to show the comment section.
 */
class CommentFragment : Fragment() {

    companion object {
        /**
         * Returns a new instance of [CommentFragment]
         */
        fun newInstance() = CommentFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.main_fragment, container, false)

    /**
     * Method used to update the comment section header
     * @param sliderHeight the height of the slider
     */
    fun updateCommentSectionHeader(sliderHeight: Int) {
        val maxSliderHeight = resources.getDimension(R.dimen.slider_max_height).toInt()
        val minSliderHeight = resources.getDimension(R.dimen.slider_min_height).toInt()
        when (sliderHeight) {
            maxSliderHeight -> TextViewCompat.setTextAppearance(message, R.style.CommentSectionOpen)
            minSliderHeight -> {
                TextViewCompat.setTextAppearance(message, R.style.CommentSectionClosed)
            }
        }
    }
}
