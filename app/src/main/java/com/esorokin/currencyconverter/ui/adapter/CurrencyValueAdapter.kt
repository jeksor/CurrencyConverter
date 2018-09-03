package com.esorokin.currencyconverter.ui.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpDelegate
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.esorokin.currencyconverter.R
import com.esorokin.currencyconverter.data.Currency
import com.esorokin.currencyconverter.data.CurrencyValue
import com.esorokin.currencyconverter.presentation.currency.CurrencyPresenter
import com.esorokin.currencyconverter.presentation.currency.CurrencyView
import kotlinx.android.synthetic.main.item_currency_value.view.*


class CurrencyValueAdapter(
    context: Context,
    parentMvpDelegate: MvpDelegate<out Any>
) : BaseMvpAdapter<CurrencyValueAdapter.CurrencyValueHolder>(parentMvpDelegate, "staticId") {

    init {
        setHasStableIds(true)
    }

    var items: List<CurrencyValue> = listOf()

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount() = items.size

    override fun getItemId(position: Int) = items[position].currency.hashCode().toLong()

    override fun onBindViewHolder(holder: CurrencyValueHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CurrencyValueHolder(
        inflater.inflate(R.layout.item_currency_value, parent, false),
        mvpDelegate
    )

    class CurrencyValueHolder(
        view: View,
        private val parentMvpDelegate: MvpDelegate<out Any>
    ) : RecyclerView.ViewHolder(view), CurrencyView {

        private var mvpDelegate: MvpDelegate<CurrencyValueHolder>? = null

        private lateinit var item: CurrencyValue

        @InjectPresenter
        lateinit var presenter: CurrencyPresenter

        @ProvidePresenter
        fun provideRepositoryPresenter(): CurrencyPresenter {
            return CurrencyPresenter(item)
        }

        private var textWatcher: TextWatcher? = null

        fun bind(item: CurrencyValue) {
            this.item = item
            destroyMvpDelegate()
            createMvpDelegate(item.currency.id)

            itemView.itemCurrencyValueInput.setOnFocusChangeListener { _, focused -> if (focused) presenter.userSelectNewBaseCurrencyType(item.currency) }
            setupTextWatcher()
        }

        override fun updateCurrency(currency: Currency) {
            itemView.apply {
                Glide.with(itemView)
                    .setDefaultRequestOptions(
                        RequestOptions()
                            .circleCrop()
                            .error(R.drawable.ic_unknown)
                    )
                    .load(currency.logoUrl)
                    .into(itemCurrencyValueImage)

                itemCurrencyValueShortName.text = currency.shortName
                itemCurrencyValueFullName.text = currency.fullName
            }
        }

        override fun updateValue(value: String) {
            textWatcher?.let { itemView.itemCurrencyValueInput.removeTextChangedListener(it) }
            itemView.itemCurrencyValueInput.setTextKeepState(value)
            textWatcher?.let { itemView.itemCurrencyValueInput.addTextChangedListener(it) }
        }

        override fun updateAvailability(available: Boolean) {
            itemView.itemCurrencyValueInput.alpha = if (available) 1.0f else 0.6f
        }

        private fun setupTextWatcher() {
            textWatcher?.let { itemView.itemCurrencyValueInput.removeTextChangedListener(it) }
            textWatcher = object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) = Unit

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    p0?.let { presenter.userChangeCurrencyValue(it.toString()) }
                }
            }.also {
                itemView.itemCurrencyValueInput.addTextChangedListener(it)
            }
        }

        private fun destroyMvpDelegate() {
            mvpDelegate?.apply {
                onSaveInstanceState()
                onDetach()
                onDestroyView()
            }
            mvpDelegate = null
        }

        private fun createMvpDelegate(childId: String) = MvpDelegate(this@CurrencyValueHolder).also {
            it.setParentDelegate(parentMvpDelegate, childId)
            it.onCreate()
            it.onAttach()
        }

    }
}
