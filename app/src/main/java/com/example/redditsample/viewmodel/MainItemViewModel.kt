package com.example.redditsample.viewmodel

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.databinding.ObservableField
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import com.example.redditsample.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class MainItemViewModel constructor(private val context: Context, stringUrl: String, textToDisplay: String, private val linkOutUrl: String) {
    val image = ObservableField<Drawable>()
    val displayText = ObservableField<String>()

    private val bindableFieldTarget: BindableFieldTarget

    init {
        displayText.set(textToDisplay)
        bindableFieldTarget = BindableFieldTarget(image, context.resources)
        Picasso.with(context)
            .load(stringUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(bindableFieldTarget)
    }

    fun openExternalBrowser() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(linkOutUrl))
        context.startActivity(browserIntent)
    }

    inner class BindableFieldTarget(
        private val observableField: ObservableField<Drawable>,
        private val resources: Resources
    ) : Target {
        override fun onBitmapFailed() {
            Log.e("image load error", "Error while loading up image")
            observableField.set(resources.getDrawable(R.drawable.ic_launcher_foreground))
        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            observableField.set(BitmapDrawable(resources, bitmap))
        }
    }
}