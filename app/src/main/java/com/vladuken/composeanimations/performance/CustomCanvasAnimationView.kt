package com.vladuken.composeanimations.performance

import android.animation.ArgbEvaluator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt

class CustomCanvasAnimationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    var angle = 0f

    @ColorInt
    private var color: Int = Color.GREEN
        set(value) {
            field = value
            paint.color = value
        }
    private val paint = Paint()
    private var rectSize = 0f

    fun animateViewChanges(toAngle: Float, @ColorInt toColor: Int) {
        val propColor = PropertyValuesHolder.ofInt(PROP_COLOR, color, toColor)

        ValueAnimator.ofPropertyValuesHolder(propColor).apply {
            setEvaluator(ArgbEvaluator())
            duration = 3000
            addUpdateListener { animation ->
                this@CustomCanvasAnimationView.color = animation.getAnimatedValue(PROP_COLOR) as Int
                invalidate() // performs animation as it calls onDraw() again
            }
            start()
        }

        val propAngle = PropertyValuesHolder.ofFloat(PROP_ANGLE, angle, toAngle)
        ValueAnimator.ofPropertyValuesHolder(propAngle).apply {
            duration = 3000
            addUpdateListener { animation ->
                this@CustomCanvasAnimationView.angle =
                    animation.getAnimatedValue(PROP_ANGLE) as Float
                invalidate() // performs animation as it calls onDraw() again
            }
            start()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        rectSize = h * 0.7f
    }

    override fun onDraw(canvas: Canvas) {
        // use newly updated angle and color, this onDraw function is called multiple times to
        // produce the animation

        val left = height / 2 - rectSize / 2
        val top = height / 2 - rectSize / 2

        canvas.save()
        canvas.rotate(angle, height / 2f, height / 2f)
        canvas.drawRoundRect(left, top, left + rectSize, left + rectSize, 16f.toPx, 16f.toPx, paint)
        canvas.restore()
    }

    companion object {
        private const val PROP_ANGLE = "angle"
        private const val PROP_COLOR = "color"
    }
}

val Float.toPx get() = this * Resources.getSystem().displayMetrics.density