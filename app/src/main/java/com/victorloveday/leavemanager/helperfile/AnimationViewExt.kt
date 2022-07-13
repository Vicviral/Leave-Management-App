package com.example.newdo.helperfile

import android.view.View
import android.view.animation.Animation

fun View.startAnim(animation: Animation, onEnd: () -> Unit) {
    animation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) = Unit

        override fun onAnimationEnd(animation: Animation?) {
            onEnd()
        }

        override fun onAnimationRepeat(animation: Animation?) = Unit
    })
    this.startAnimation(animation)
}