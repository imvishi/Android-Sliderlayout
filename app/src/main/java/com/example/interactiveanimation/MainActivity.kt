package com.example.interactiveanimation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.interactiveanimation.ui.main.CommentFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.slider, CommentFragment.newInstance())
                .commitNow()
        }
        val maxSliderHeight = resources.getDimension(R.dimen.slider_max_height)
        slider.addOnLayoutChangeListener { _, _, top, _, bottom, _, _, _, _ ->
            val alpha = (bottom - top) / maxSliderHeight
            overlay.alpha = alpha
        }
    }
}
