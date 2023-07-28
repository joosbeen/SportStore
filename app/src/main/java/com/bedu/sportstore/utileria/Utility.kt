package com.bedu.sportstore.utileria

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.bedu.sportstore.R
import com.google.android.material.snackbar.Snackbar
import java.util.Date

object Utility {

    //display SnackBar
    fun displaySnackBar(view: View, s: String, context: Context, @ColorRes colorRes: Int = R.color.green, length: Int = Snackbar.LENGTH_SHORT) {

        Snackbar.make(view, s, length)
            .withColor(ContextCompat.getColor(context, colorRes))
            .setTextColor(ContextCompat.getColor(context, R.color.white))
            .show()

    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun Snackbar.withColor(@ColorInt colorInt: Int): Snackbar {
        this.view.setBackgroundColor(colorInt)
        return this
    }

    fun getDate_ddMMYYYY(date: Date): String {
        return "${validDayAndMonth(date.date)}/${validDayAndMonth(date.month+1)}/${date.year}"
    }

    private fun validDayAndMonth(valid: Int): String {
        return if (valid<10) "0${valid}" else "$valid"
    }

}