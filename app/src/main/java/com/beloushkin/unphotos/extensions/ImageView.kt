package com.beloushkin.unphotos.extensions

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.beloushkin.unphotos.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadNetworkImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}