package com.scrum.movieapp.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.scrum.movieapp.R

class Button : AppCompatButton {
    private lateinit var buttonEnabled: Drawable
    private lateinit var buttonDisabled: Drawable

    constructor(context: Context, attrs: AttributeSet, defStyleAttrs: Int) : super(context, attrs, defStyleAttrs) {
        initButton()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initButton()
    }

    constructor(context: Context) : super(context) {
        initButton()
    }

    private fun initButton() {
        val textTone = ContextCompat.getColor(context, R.color.white)
        val enabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_enabled) as Drawable
        val disabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_disabled) as Drawable

        setBackgroundDrawable(null)
        textSize = 12f
        setTextColor(textTone)
        gravity = Gravity.CENTER
        letterSpacing = 0.1f
        isAllCaps = true

        buttonEnabled = enabledBackground
        buttonDisabled = disabledBackground
    }

    override fun onDraw(canvas: Canvas) {
        canvas.let { super.onDraw(it) }
        background = if (isEnabled) {
            buttonEnabled
        } else {
            buttonDisabled
        }
    }

}