package com.fernandocejas.sample.core.navigation

import android.content.Context
import javax.inject.Singleton

interface Navigator {

    fun openVideo(context: Context, videoUrl: String)
}