package com.esorokin.currencyconverter.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpDelegate

abstract class BaseMvpAdapter<VH : RecyclerView.ViewHolder>(
    parentDelegate: MvpDelegate<*>,
    childId: String
) : RecyclerView.Adapter<VH>() {

    protected val mvpDelegate by lazy {
        MvpDelegate(this@BaseMvpAdapter).also {
            it.setParentDelegate(parentDelegate, childId)
        }
    }

    init {
        mvpDelegate.onCreate()
    }
}
