package com.vladuken.composeanimations.performance

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.vladuken.composeanimations.R

class PerformanceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_performance)

        val root = findViewById<LinearLayout>(R.id.root)
        root.clipChildren = false
        root.setBackgroundColor(Color.White.toArgb())
        val animatables = mutableListOf<CustomCanvasAnimationView>()

        for (i in 0 until ROWS) {

            val ll = LinearLayout(this)
            ll.orientation = LinearLayout.HORIZONTAL
            ll.clipChildren = false
            ll.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            for (j in 0 until COLS) {
                val anim =
                    CustomCanvasAnimationView(this).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            BOX_SIZE_DP.toFloat().toPx.toInt(),
                            BOX_SIZE_DP.toFloat().toPx.toInt()
                        )
                    }
                ll.addView(anim)
                animatables.add(anim)
            }

            root.addView(ll)
        }

        val handler = Handler(Looper.getMainLooper())
        var index = 0

        val run = object : Runnable {
            override fun run() {
                val need = index % colors.size
                animatables.forEach {
                    it.angle = 0f
                    it.animateViewChanges(360f, colors[need].toArgb())
                }
                ++index
                handler.postDelayed(this, 3000L)
            }
        }

        handler.post(run)

    }
}

val BOX_SIZE_DP = 100
val ROWS = 6
val COLS = 4

val colors = arrayOf(Color.Blue, Color.Gray, Color.Magenta, Color.Cyan, Color.Red)