package com.prl.android.covid19india.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflateLayout(layout: Int): View =
    LayoutInflater.from(context).inflate(layout, this, false)