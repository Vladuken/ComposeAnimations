package com.vladuken.composeanimations.legacyanim

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.fragment.app.Fragment
import com.vladuken.composeanimations.R


class LeftRightFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_left_right, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topLeftButton = view.findViewById<Button>(R.id.button_top_left)
        val topRightButton = view.findViewById<Button>(R.id.button_top_right)

        val rect = view.findViewById<SampleRect>(R.id.rect)
        view.setBackgroundColor(Color.WHITE)

        val duration = 1000L

        view.post {
            reset(view, rect)
            rect.visibility = View.VISIBLE

            rect.setOnClickListener { reset(view, rect) }

            val animx = SpringAnimation(rect, DynamicAnimation.TRANSLATION_X)
            val animy = SpringAnimation(rect, DynamicAnimation.TRANSLATION_Y)

            animx.spring = SpringForce()
            animy.spring = SpringForce()

            animx.spring.dampingRatio = SpringForce.DAMPING_RATIO_NO_BOUNCY
            animx.spring.stiffness = SpringForce.STIFFNESS_VERY_LOW

            animy.spring.dampingRatio = SpringForce.DAMPING_RATIO_NO_BOUNCY
            animy.spring.stiffness = SpringForce.STIFFNESS_VERY_LOW

            topLeftButton.setOnClickListener {
                animx.animateToFinalPosition(40f)
                animy.animateToFinalPosition(40f)
            }

            topRightButton.setOnClickListener {
                animx.animateToFinalPosition((view.width - rect.width - 40f).toFloat())
                animy.animateToFinalPosition(40f)
            }
        }
    }

    private fun reset(root: View, rect: SampleRect) {
        val left = root.width / 2 - rect.width / 2
        val top = root.height / 2 - rect.height / 2

        rect.translationX = left.toFloat()
        rect.translationY = top.toFloat()
    }
}