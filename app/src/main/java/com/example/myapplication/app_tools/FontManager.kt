package com.example.myapplication.app_tools

import android.content.Context
import android.graphics.Typeface

object FontManager {
    private var customTypeface: Typeface? = null

    fun setTypeface(context: Context, fontName: String?) {
        customTypeface = Typeface.createFromAsset(context.assets, fontName)
    }

    fun getTypeface(): Typeface? {
        return customTypeface
    }
}