package com.example.kyleamyx.luckycoins.list.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.kyleamyx.luckycoins.R
import com.example.kyleamyx.luckycoins.StorageUtils
import com.example.kyleamyx.luckycoins.favorites.FavoriteCoin
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
                coin.quoteItem?.quoteUSD?.priceUSD)
        itemView.setOnClickListener(this)

        if (coin.tags!!.contains(itemView.getStringResource(R.string.list_item_mineable))) {
            itemView.listItemLogo.setImageResource(R.mipmap.bitcoin_mine)
        } else {
            itemView.listItemLogo.setImageResource(R.mipmap.no_bitcoin_mine)
        }

        if (StorageUtils.isItemAFavorite(coin.id!!, itemView.context)) {
            itemView.favoritesBtn.visibility = View.INVISIBLE
        } else {
            itemView.favoritesBtn.setOnClickListener {
                //call dao here
                listener?.onFavoriteClicked(FavoriteCoin(coin.id.toInt(), coin.slug, coin.name, coin.symbol))
            }
        }
    }

    override fun onClick(v: View?) {
        listener?.onCoinClicked(coinItem)
    }


    // String Resource Ext. Function
    fun View.getStringResource(resourceId: Int): String = this.context.getString(resourceId)

}