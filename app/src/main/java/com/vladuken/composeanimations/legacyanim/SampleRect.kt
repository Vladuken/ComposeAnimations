package com.vladuken.composeanimations.legacyanim

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Outline
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider

class SampleRect @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {


    init {
        setWillNotDraw(false)
        clipToOutline = true
        outlineProvider = object : ViewOutlineProvider() {

            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, view.width, view.height, 20f)
            }
        }
    }

    private val _color = Color.parseColor("#916E99")

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawColor(_color)
    }
}