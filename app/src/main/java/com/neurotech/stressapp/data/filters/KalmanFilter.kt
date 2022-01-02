package com.neurotech.stressapp.data.filters

class KalmanFilter(val F: Double = 1.0, val H:Double = 1.0, val Q:Double = 2.0, val R:Double = 40.0) {
    private var X0: Double? = null
    private var P0: Double? = null
    private var state: Double? = null
    private var covariance: Double? = null

    public fun setState(state: Double, covariance: Double){
        this.state = state
        this.covariance = covariance
    }

    public fun correct(value: Double) : Double{
        X0 = F* state!!
        P0 = F* covariance!! *F + Q

        val K = H* P0!! /(H*P0!!*H + R)
        state = X0!! + K*(value - H*X0!!)
        covariance = (1 - K*H)*P0!!
        return state!!
    }
}