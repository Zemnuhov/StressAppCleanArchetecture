package com.neurotech.stressapp.data.filters

class KalmanFilter(
    private var state: Double,
    private var covariance: Double,
    val F: Double = 1.0,
    val H: Double = 1.0,
    val Q: Double = 2.0,
    val R: Double = 40.0
) {

    public fun correct(value: Double): Double {
        val x = F * state
        val p = F * covariance * F + Q

        val K = H * p / (H * p * H + R)
        state = x + K * (value - H * x)
        covariance = (1 - K * H) * p
        return state
    }
    public fun correct(value: Int): Double {
        val x = F * state
        val p = F * covariance * F + Q

        val K = H * p / (H * p * H + R)
        state = x + K * (value.toDouble() - H * x)
        covariance = (1 - K * H) * p
        return state
    }
}