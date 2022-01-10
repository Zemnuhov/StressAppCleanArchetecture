package com.neurotech.stressapp.data.filters

class ExpRunningAverage {
    var k = 0.1 // коэффициент фильтрации, 0.0-1.0
    var filVal = 0.0

    public fun filter(newVal: Int): Double {
        filVal += (newVal.toDouble() - filVal) * k
        return filVal
    }
    public fun filter(newVal: Double): Double {
        filVal += (newVal.toDouble() - filVal) * k
        return filVal
    }
}