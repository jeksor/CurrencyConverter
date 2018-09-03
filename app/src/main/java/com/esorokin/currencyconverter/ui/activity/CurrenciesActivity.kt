package com.esorokin.currencyconverter.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.esorokin.currencyconverter.R
import com.esorokin.currencyconverter.data.CurrencyValue
import com.esorokin.currencyconverter.presentation.list.CurrenciesPresenter
import com.esorokin.currencyconverter.presentation.list.CurrenciesView
import com.esorokin.currencyconverter.ui.adapter.CurrencyValueAdapter
import kotlinx.android.synthetic.main.activity_converter.*

class CurrenciesActivity : BaseActivity(), CurrenciesView {

    @InjectPresenter
    lateinit var presenter: CurrenciesPresenter

    private val currenciesAdapter by lazy {
        CurrencyValueAdapter(this@CurrenciesActivity, mvpDelegate)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)

        activityConverterCurrenciesList.apply {
            layoutManager = LinearLayoutManager(this@CurrenciesActivity, LinearLayoutManager.VERTICAL, false)
            adapter = currenciesAdapter
        }
    }

    override fun showCurrencies(currencies: List<CurrencyValue>) {
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val old = currenciesAdapter.items[oldItemPosition]
                val new = currencies[newItemPosition]
                return old.currency == new.currency
            }

            override fun getOldListSize() = currenciesAdapter.itemCount

            override fun getNewListSize() = currencies.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val old = currenciesAdapter.items[oldItemPosition]
                val new = currencies[newItemPosition]
                return old.currency == new.currency
            }

        }).also {
            currenciesAdapter.items = currencies
            it.dispatchUpdatesTo(currenciesAdapter)
        }
    }
}
