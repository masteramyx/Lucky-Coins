package com.example.kyleamyx.luckycoins.list.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.kyleamyx.luckycoins.R
import com.example.kyleamyx.luckycoins.models.CoinListItem
import kotlinx.android.synthetic.main.coin_list_item.view.*

/**
 * Created by kyleamyx on 6/23/18.
 */
class CoinListViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    var listener: CoinListAdapter.CoinListListener? = null

    lateinit var coinItem: CoinListItem

    fun bindView(coin: CoinListItem) {
        coinItem = coin
        itemView.coinName.text = coin.name
        itemView.coinSymbol.text = coin.symbol
        itemView.coinListPrice.text = String.format(itemView.context.getString(R.string.list_item_price),
                coin.quoteItem.quoteUSD.priceUSD)
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        listener?.onCoinClicked(coinItem)
    }
}