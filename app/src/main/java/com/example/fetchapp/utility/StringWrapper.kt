package com.example.fetchapp.utility

import android.content.Context
import androidx.annotation.StringRes

sealed class StringWrapper {
    /**
     * abstract method to return string representation of StringWrapper in [context]
     */
    abstract fun getFormattedString(context: Context): String

    companion object {
        val EMPTY = BasicString("")
    }
}

data class BasicString(val stringValue: String) : StringWrapper() {
    override fun getFormattedString(context: Context): String = stringValue
}

/**
 * This class represent Android single string-resource [stringId]
 */
data class SingleString(
    @StringRes val stringId: Int
) : StringWrapper() {
    override fun getFormattedString(context: Context): String =
        context.getString(stringId)
}

fun String.asStringWrapper(): StringWrapper {
    return if (this.isEmpty()) StringWrapper.EMPTY else BasicString(this)
}

fun Int.asSingleString(): SingleString {
    return SingleString(this)
}
