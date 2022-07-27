package com.rorono.a22recycler.utils

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView

class CustomCardView(context: Context, attrs:AttributeSet):CardView(context,attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}
