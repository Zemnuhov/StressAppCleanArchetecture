package com.neurotech.stressapp.ui.fragment.mainfragmentitem

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import com.neurotech.stressapp.R

class ScaleView : Fragment() {
    private var val1000g: LinearLayout? = null
    private var val2000g: LinearLayout? = null
    private var val3000g: LinearLayout? = null
    private var val4000g: LinearLayout? = null
    private var val4500y: LinearLayout? = null
    private var val5500y: LinearLayout? = null
    private var val6500y: LinearLayout? = null
    private var val7500r: LinearLayout? = null
    private var val8000r: LinearLayout? = null
    private var linearStates: HashMap<LinearLayout?, ArrayList<Int>>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.tonic_scale, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        linearStates = HashMap()
        val1000g = view.findViewById(R.id.val_1000)
        val2000g = view.findViewById(R.id.val_2000)
        val3000g = view.findViewById(R.id.val_3000)
        val4000g = view.findViewById(R.id.val_4000)
        val4500y = view.findViewById(R.id.val_4500)
        val5500y = view.findViewById(R.id.val_5500)
        val6500y = view.findViewById(R.id.val_6500)
        val7500r = view.findViewById(R.id.val_7500)
        val8000r = view.findViewById(R.id.val_8000)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hashMapInit()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun hashMapInit() {
        linearStates!![val1000g] = ArrayList(
            arrayListOf(
                context?.getColor(R.color.green_not_active)!!,
                context?.getColor(R.color.green_active)!!
            )
        )

        linearStates!![val2000g] = ArrayList(
            arrayListOf(
                context?.getColor(R.color.green_not_active)!!,
                context?.getColor(R.color.green_active)!!
            )
        )
        linearStates!![val3000g] = ArrayList(
            arrayListOf(
                context?.getColor(R.color.green_not_active)!!,
                context?.getColor(R.color.green_active)!!
            )
        )
        linearStates!![val4000g] = ArrayList(
            arrayListOf(
                context?.getColor(R.color.green_not_active)!!,
                context?.getColor(R.color.green_active)!!
            )
        )
        linearStates!![val4500y] = ArrayList(
            arrayListOf(
                context?.getColor(R.color.yellow_not_active)!!,
                context?.getColor(R.color.yellow_active)!!
            )
        )
        linearStates!![val5500y] = ArrayList(
            arrayListOf(
                context?.getColor(R.color.yellow_not_active)!!,
                context?.getColor(R.color.yellow_active)!!
            )
        )
        linearStates!![val6500y] = ArrayList(
            arrayListOf(
                context?.getColor(R.color.red_not_active)!!,
                context?.getColor(R.color.red_active)!!
            )
        )
        linearStates!![val7500r] = ArrayList(
            arrayListOf(
                context?.getColor(R.color.red_not_active)!!,
                context?.getColor(R.color.red_active)!!
            )
        )
        linearStates!![val8000r] = ArrayList(
            arrayListOf(
                context?.getColor(R.color.red_not_active)!!,
                context?.getColor(R.color.red_active)!!
            )
        )
    }


    private fun activated(linear: LinearLayout) {
        linear.setBackgroundColor(linearStates!![linear]!![1])
    }

    private fun deactivated(linear: LinearLayout) {
        linear.setBackgroundColor(linearStates!![linear]!![0])
    }

    private fun conditions(value: Int, border: Int, linear: LinearLayout) {
        if (value > border) {
            activated(linear)
        } else {
            deactivated(linear)
        }
    }

    fun setScale(value: Int) {
        conditions(value, 1000, val1000g!!)
        conditions(value, 2000, val2000g!!)
        conditions(value, 3000, val3000g!!)
        conditions(value, 4000, val4000g!!)
        conditions(value, 4500, val4500y!!)
        conditions(value, 5500, val5500y!!)
        conditions(value, 6500, val6500y!!)
        conditions(value, 7500, val7500r!!)
        conditions(value, 8000, val8000r!!)
    }

}