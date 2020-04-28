package com.example.interactiveanimation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.interactiveanimation.ui.main.CommentFragment
import kotlinx.android.synthetic.main.main_activity.*

/**
 * The main activity of the app. This is the first activity that will render on screen when
 *  user starts the app.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val commentFragment: CommentFragment
        if (savedInstanceState == null) {
            commentFragment = CommentFragment.newInstance()
            showFragment(commentFragment)
        } else {
            commentFragment = supportFragmentManager.fragments.first() as CommentFragment
        }

        val maxSliderHeight = resources.getDimension(R.dimen.slider_max_height)
        slider.addOnLayoutChangeListener { _, _, top, _, bottom, _, _, _, _ ->
            // Calculate overlay alpha value
            val alpha = (bottom - top) / maxSliderHeight
            overlay.alpha = alpha
            // Update comment section header
            commentFragment.updateCommentSectionHeader(sliderHeight = bottom - top)
        }
    }

    /**
     * method used to show the new fragment
     * @param fragment the fragment
     */
    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.slider, fragment).commitNow()
    }
}
