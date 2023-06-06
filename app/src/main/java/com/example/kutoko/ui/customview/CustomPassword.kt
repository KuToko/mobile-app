package com.example.kutoko.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.kutoko.R
import java.util.regex.Pattern

class CustomPassword : AppCompatEditText {

    private lateinit var lockImage: Drawable

    private val Password_Pattern = Pattern.compile("^" +
            //"(?=.*[@#$%^&+=])" +     // at least 1 special character
            "(?=\\S+$)" +            // no white spaces
            ".{8,}" +                // at least 8 characters
            "$")

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

        hint = resources.getString(R.string.kata_sandi)
        textAlignment = View.TEXT_ALIGNMENT_TEXT_START
    }


    private fun init() {
        lockImage = ContextCompat.getDrawable(context, R.drawable.ic_lock) as Drawable
        setCompoundDrawablesWithIntrinsicBounds(lockImage, null, null, null)

        addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()){
                    error = resources.getString(R.string.kolom_kosong)
                }
                else if (s.toString().isNotEmpty() && Password_Pattern.matcher(s).matches()){
                    error = null
                } else {
                    error = resources.getString(R.string.invalid_sandi)
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }
}