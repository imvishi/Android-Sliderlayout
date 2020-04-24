package com.example.interactiveanimation.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.interactiveanimation.R
import kotlinx.android.synthetic.main.main_fragment.*

/**
 * Fragment to show the comment section.
 */
class CommentFragment : Fragment() {

    companion object {
        fun newInstance() = CommentFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.main_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val maxSliderHeight = resources.getDimension(R.dimen.slider_max_height).toInt()
        val minSliderHeight = resources.getDimension(R.dimen.slider_min_height).toInt()
        (main.parent as View).addOnLayoutChangeListener { _, _, top, _, bottom, _, _, _, _ ->
            when ((bottom - top)) {
                maxSliderHeight -> message.setTextAppearance(R.style.CommentSectionOpen)
                minSliderHeight -> message.setTextAppearance(R.style.CommentSectionClosed)
            }
        }
    }
}
