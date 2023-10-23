package com.scrum.movieapp.customview

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.scrum.movieapp.R

class Password : AppCompatEditText {
    constructor(context: Context, attrs: AttributeSet, defStyleAttrs: Int) : super(context, attrs, defStyleAttrs) {
        initPassword()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initPassword()
    }

    constructor(context: Context) : super(context) {
        initPassword()
    }

    private fun initPassword() {
        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                verifyError(s)
            }
        })
    }

    private fun verifyError(string: CharSequence) {
        val errorMessage = if (string.isNotEmpty() && string.length < 8) {
            context.getString(R.string.password_invalid)
        } else {
            null
        }

        error = errorMessage
        if (errorMessage != null) {
            requestFocus()
        }
    }
}