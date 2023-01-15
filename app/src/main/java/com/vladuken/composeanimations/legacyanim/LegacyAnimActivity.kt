package com.vladuken.composeanimations.legacyanim

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.math.MathUtils
import com.vladuken.composeanimations.R

class LegacyAnimActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_legacy_anim)

        val button = findViewById<Button>(R.id.button_change_size)
        val view = findViewById<View>(R.id.rect)
        val root = findViewById<View>(R.id.root)

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

                    val cur = view.width

                    val fraction = it.animatedFraction
                    val size = MathUtils.lerp(cur.toFloat(), endPx, fraction)

                    val params = view.layoutParams as ConstraintLayout.LayoutParams
                    params.width = size.toInt()
                    params.height = size.toInt()

                    view.layoutParams = params
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

                    val cur = view.width

                    val fraction = it.animatedFraction
                    val size = MathUtils.lerp(cur.toFloat(), startPx, fraction)

                    val params = view.layoutParams as ConstraintLayout.LayoutParams
                    params.width = size.toInt()
                    params.height = size.toInt()

                    view.layoutParams = params
                }

                animator.start()

            }


            button.text = if (isSmall) "make big" else "make small"
        }


    }
}