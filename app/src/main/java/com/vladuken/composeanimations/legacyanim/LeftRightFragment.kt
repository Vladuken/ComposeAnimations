package com.vladuken.composeanimations.legacyanim

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.math.MathUtils
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

            var animator: ValueAnimator? = null

            topLeftButton.setOnClickListener {

                animator?.cancel()
                animator?.removeAllUpdateListeners()

                val startTop = (rect.layoutParams as ConstraintLayout.LayoutParams).topMargin
                val startLeft = (rect.layoutParams as ConstraintLayout.LayoutParams).leftMargin

                animator = ObjectAnimator.ofFloat(0f, 1f)
                    .setDuration(duration)

                animator?.addUpdateListener {

                    val params = rect.layoutParams as ConstraintLayout.LayoutParams
                    val fraction = it.animatedFraction

                    val left = MathUtils.lerp(startLeft.toFloat(), 0f, fraction)
                    val top = MathUtils.lerp(startTop.toFloat(), 0f, fraction)


                    params.topMargin = top.toInt()
                    params.leftMargin = left.toInt()

                    rect.layoutParams = params
                }

                animator?.start()

            }

            topRightButton.setOnClickListener {

                animator?.cancel()
                animator?.removeAllUpdateListeners()

                val startTop = (rect.layoutParams as ConstraintLayout.LayoutParams).topMargin
                val startLeft = (rect.layoutParams as ConstraintLayout.LayoutParams).leftMargin

                animator = ObjectAnimator.ofFloat(0f, 1f)
                    .setDuration(duration)

                animator?.addUpdateListener {

                    val params = rect.layoutParams as ConstraintLayout.LayoutParams
                    val fraction = it.animatedFraction

                    val left = MathUtils.lerp(
                        startLeft.toFloat(),
                        (view.width - rect.width).toFloat(),
                        fraction
                    )
                    val top = MathUtils.lerp(startTop.toFloat(), 0f, fraction)


                    params.topMargin = top.toInt()
                    params.leftMargin = left.toInt()

                    rect.layoutParams = params
                }

                animator?.start()

            }
        }
    }

    private fun reset(root: View, rect: SampleRect) {
        val params = rect.layoutParams as ConstraintLayout.LayoutParams
        val left = root.width / 2 - rect.width / 2
        val top = root.height / 2 - rect.height / 2

        params.leftMargin = left
        params.topMargin = top

        rect.layoutParams = params
    }
}