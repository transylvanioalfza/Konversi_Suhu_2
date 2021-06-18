package org.d3if4118.assessment2.utils

import android.view.View
import android.widget.TextView
import org.d3if4118.assessment2.model.Articles

var DATA_NEWS: Articles = Articles()
fun TextView.toShow() {
    visibility = View.VISIBLE
}