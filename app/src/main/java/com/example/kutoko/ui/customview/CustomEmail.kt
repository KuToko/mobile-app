package com.example.kutoko.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.view.marginStart
import com.example.kutoko.R

class CustomEmail : AppCompatEditText {

    private lateinit var userImage: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas : Canvas){
        super.onDraw(canvas)

        hint = resources.getString(R.string.alamat_email)
        textAlignment = View.TEXT_ALIGNMENT_TEXT_START
    }


    private fun init() {
        userImage = ContextCompat.getDrawable(context, R.drawable.ic_user_black) as Drawable
        setCompoundDrawablesWithIntrinsicBounds(userImage, null, null, null)

        addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()){
                    error = resources.getString(R.string.kolom_kosong)
                }
                else if (s.toString().isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(s).matches()){
                    error = null
                } else {
                    error = resources.getString(R.string.invalid_email)
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }
}