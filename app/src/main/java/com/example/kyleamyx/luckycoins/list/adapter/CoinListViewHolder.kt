package com.example.kyleamyx.luckycoins.list.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.kyleamyx.luckycoins.R
import com.example.kyleamyx.luckycoins.favorites.db.CoinFavoriteRepository
import com.example.kyleamyx.luckycoins.models.CoinFavoriteItem
import com.example.kyleamyx.luckycoins.models.CoinListItem
import kotlinx.android.synthetic.main.coin_list_item.view.*
import org.koin.core.context.GlobalContext.get

/**
 * Created by kyleamyx on 6/23/18.
 */
class CoinListViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    var listener: CoinListAdapter.CoinListListener? = null

    val favoritesRepository: CoinFavoriteRepository = get().koin.get()

    val favoritesList = favoritesRepository.getFavorites().blockingGet()

    lateinit var coinItem: CoinListItem

    fun bindView(coin: CoinListItem) {
        coinItem = coin
        itemView.coinName.text = coin.name
        itemView.coinSymbol.text = coin.symbol
        itemView.coinListPrice.text = String.format(itemView.context.getString(R.string.list_item_price),
                coin.quoteItem?.quoteUSD?.priceUSD)
        itemView.setOnClickListener(this)

        if (coin.tags!!.contains(itemView.getStringFromResource(R.string.list_item_mineable))) {
            itemView.listItemLogo.setImageResource(R.mipmap.bitcoin_mine)
        } else {
            itemView.listItemLogo.setImageResource(R.mipmap.no_bitcoin_mine)
        }

        if (favoritesList.contains(coin.toFavoriteItem())) {
            itemView.favoritesBtn.visibility = View.INVISIBLE
        } else {
            //Without setting to visible, the viewholder is getting confused and randomly removing the button.
            itemView.favoritesBtn.visibility = View.VISIBLE
            itemView.favoritesBtn.setOnClickListener {
                //call dao here
                listener?.onFavoriteClicked(CoinFavoriteItem(coin.id.toInt(),
                        coin.slug,
                        coin.name,
                        coin.symbol))
            }
        }
    }


    override fun onClick(v: View?) {
        listener?.onCoinClicked(coinItem)
    }


    // String Resource Ext. Function
    fun View.getStringFromResource(resourceId: Int): String = this.context.getString(resourceId)

}