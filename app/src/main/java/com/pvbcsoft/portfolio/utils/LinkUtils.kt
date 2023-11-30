package com.pvbcsoft.portfolio.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

class LinkUtils(private val context: Context) {
    fun openLink(link: String) {
        val uri = Uri.parse(link)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }
}