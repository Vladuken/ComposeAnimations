package com.vladuken.composeanimations.legacyanim

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.math.MathUtils
import com.vladuken.composeanimations.R

class BigSmallFragment: Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_big_small, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = view.findViewById<Button>(R.id.button_change_size)
        val rect = view.findViewById<View>(R.id.rect)
        val root = view.findViewById<View>(R.id.root)

        root.setBackgroundColor(Color.WHITE)

        val density = resources.displayMetrics.density
        val startPx = 100f * density
        val endPx = 250f * density

        var isSmall = true
        button.text = if (isSmall) "make big" else "make small"

        button.setOnClickListener {

            var animator: ValueAnimator? = null

            if (isSmall) {
                isSmall = false

                animator?.cancel()
                animator?.removeAllUpdateListeners()

                animator =
                    ObjectAnimator.ofFloat(0f, 1f)
                        .setDuration(1000L)

                animator.interpolator = LinearInterpolator()
                animator.addUpdateListener {

                    val cur = rect.width

                    val fraction = it.animatedFraction
                    val size = MathUtils.lerp(cur.toFloat(), endPx, fraction)

                    val params = rect.layoutParams as ConstraintLayout.LayoutParams
                    params.width = size.toInt()
                    params.height = size.toInt()

                    rect.layoutParams = params
                }

                animator.start()


            } else {
                isSmall = true

                animator?.cancel()
                animator?.removeAllUpdateListeners()

                animator =
                    ObjectAnimator.ofFloat(0f, 1f)
                        .setDuration(1000L)

                animator.interpolator = LinearInterpolator()
                animator.addUpdateListener {

                    val cur = rect.width

                    val fraction = it.animatedFraction
                    val size = MathUtils.lerp(cur.toFloat(), startPx, fraction)

                    val params = rect.layoutParams as ConstraintLayout.LayoutParams
                    params.width = size.toInt()
                    params.height = size.toInt()

                    rect.layoutParams = params
                }

                animator.start()

            }


            button.text = if (isSmall) "make big" else "make small"
        }
    }
}