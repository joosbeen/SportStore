package com.bedu.sportstore.ui.toolbar

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bedu.sportstore.R

class ToolbarBasic {
    fun show(activity: AppCompatActivity, title: String, backButton: Boolean): Toolbar {
        val toolbar: Toolbar = activity.findViewById(R.id.toolbarGlobal)
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.title = title
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(backButton)
        return toolbar
    }
}