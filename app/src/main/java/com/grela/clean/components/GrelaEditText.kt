package com.grela.clean.components

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import com.google.android.material.textfield.TextInputLayout.END_ICON_NONE
import com.google.android.material.textfield.TextInputLayout.END_ICON_PASSWORD_TOGGLE
import com.grela.clean.R
import kotlinx.android.synthetic.main.layout_grela_edit_text.view.*

class GrelaEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var hint: String = ""
        get() {
            return grelaEditTextInputLayoutContainer.hint.toString()
        }
        set(value) {
            field = value
            grelaEditTextInputLayoutContainer.hint = value
        }

    var text: String = ""
        get() {
            return grelaEditTextEditText.text.toString()
        }
        set(value) {
            field = value
            grelaEditTextEditText.setText(value)
        }

    var maxLength = 0
        set(value) {
            if (value > 0) {
                val filters = grelaEditTextEditText.filters.toMutableList()
                filters.add(InputFilter.LengthFilter(value))
                grelaEditTextEditText.filters = filters.toTypedArray()
            }
        }

    var error: String = ""
        set(value) {
            field = value
            grelaEditTextInputLayoutContainer.error = value
        }

    var inputType = 0
        set(value) {
            field = value
            grelaEditTextEditText.inputType = value
        }

    var imeOption = 0
        set(value) {
            field = value
            grelaEditTextEditText.imeOptions = value
        }

    var passwordToggleEnabled = false
        set(value) {
            field = value
            grelaEditTextInputLayoutContainer.endIconMode = if (value) END_ICON_PASSWORD_TOGGLE else END_ICON_NONE
        }

    fun setOnEnterPressedListener(function: () -> Unit) {
        grelaEditTextEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_DONE) {
                function.invoke()
            }
            true
        }
    }

    fun hideError() {
        grelaEditTextInputLayoutContainer.error = ""
    }


    init {
        inflate(context, R.layout.layout_grela_edit_text, this)

        context.theme.obtainStyledAttributes(attrs, R.styleable.GrelaEditText, defStyleAttr, 0).apply {
            text = getResourceId(R.styleable.GrelaEditText_text, 0).let { if (it != 0) context.getString(it) else getString(R.styleable.GrelaEditText_text) ?: "" }
            hint = getResourceId(R.styleable.GrelaEditText_hint, 0).let { if (it != 0) context.getString(it) else getString(R.styleable.GrelaEditText_hint) ?: "" }
            maxLength = getInteger(R.styleable.GrelaEditText_maxLength, 0)
            inputType = getInteger(R.styleable.GrelaEditText_android_inputType, 0)
            imeOption = getInteger(R.styleable.GrelaEditText_android_imeOptions, 0)
            passwordToggleEnabled = getBoolean(R.styleable.GrelaEditText_passwordToggleEnabled, false)
            recycle()
        }

        grelaEditTextEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = hideError()

            override fun afterTextChanged(p0: Editable?) = Unit
        })
    }
}