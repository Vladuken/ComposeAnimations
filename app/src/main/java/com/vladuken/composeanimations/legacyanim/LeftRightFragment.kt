package com.vladuken.composeanimations.legacyanim

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

                val startTop = rect.translationY
                val startLeft = rect.translationX

                animator = ObjectAnimator.ofFloat(0f, 1f)
                    .setDuration(duration)

                animator?.addUpdateListener {
                    val fraction = it.animatedFraction

                    val left = MathUtils.lerp(startLeft, 40f, fraction)
                    val top = MathUtils.lerp(startTop, 40f, fraction)

                    rect.translationX = left
                    rect.translationY = top
                }

                animator?.start()

            }

            topRightButton.setOnClickListener {

                animator?.cancel()
                animator?.removeAllUpdateListeners()

                val startTop = rect.translationY
                val startLeft = rect.translationX

                animator = ObjectAnimator.ofFloat(0f, 1f)
                    .setDuration(duration)

                animator?.addUpdateListener {

                    val fraction = it.animatedFraction

                    val left = MathUtils.lerp(
                        startLeft,
                        (view.width - rect.width - 40f).toFloat(),
                        fraction
                    )
                    val top = MathUtils.lerp(startTop, 40f, fraction)

                    rect.translationX = left
                    rect.translationY = top
                }

                animator?.start()
            }
        }
    }

    private fun reset(root: View, rect: SampleRect) {
        val left = root.width / 2 - rect.width / 2
        val top = root.height / 2 - rect.height / 2

        rect.translationX = left.toFloat()
        rect.translationY = top.toFloat() + 100f
    }
}