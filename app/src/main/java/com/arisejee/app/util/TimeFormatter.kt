package com.arisejee.app.util

object TimeFormatter {
    fun minutesToDisplay(minutes: Int): String {
        val h = minutes / 60
        val m = minutes % 60
        return when {
            h > 0 && m > 0 -> "${'$'}{h}h ${'$'}{m}m"
            h > 0 -> "${'$'}{h}h"
            else -> "${'$'}{m}m"
        }
    }
    fun secondsToTimer(seconds: Long): String {
        val h = seconds / 3600
        val m = (seconds % 3600) / 60
        val s = seconds % 60
        return if (h > 0) String.format("%d:%02d:%02d", h, m, s)
        else String.format("%02d:%02d", m, s)
    }
}
