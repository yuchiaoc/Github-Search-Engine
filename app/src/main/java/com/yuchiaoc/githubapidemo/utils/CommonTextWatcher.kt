package com.yuchiaoc.githubapidemo.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.regex.Pattern

class CommonTextWatcher(private val editText: EditText) : TextWatcher {
    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        val limitedEx = "[\n!?@:'\"|^$()/*><#=%+&;\\\\]"
        val pattern = Pattern.compile(limitedEx)
        val m = pattern.matcher(p0.toString())
        if (m.find()) {
            val replacedString: String = p0.toString().replace(limitedEx.toRegex(), "")
            editText.setText(replacedString)
            editText.setSelection(replacedString.length)
        }
    }
}