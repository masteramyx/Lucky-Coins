package com.example.kyleamyx.luckycoins.list.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.kyleamyx.luckycoins.R
import com.example.kyleamyx.luckycoins.favorites.db.CoinFavoriteRepository
import com.example.kyleamyx.luckycoins.getStringFromResource
import com.example.kyleamyx.luckycoins.models.CoinFavoriteItem
import com.example.kyleamyx.luckycoins.models.CoinListItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.coin_list_item.view.*
import org.koin.core.context.GlobalContext.get

/**
 * Created by kyleamyx on 6/23/18.
 */
class CoinListViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    var listener: CoinListAdapter.CoinListListener? = null

    private val favoritesRepository: CoinFavoriteRepository = get().koin.get()
    //todo - don't do blocking, find a better way to get favorites list here...maybe pass it in or inject?
    private val favoritesList = favoritesRepository.getFavorites().blockingGet()

    private lateinit var coinItem: CoinListItem

    fun bindView(coin: CoinListItem) {
        coinItem = coin
        itemView.apply {
            coinName.text = coin.name
            coinSymbol.text = coin.symbol
            coinListPrice.text = String.format(itemView.getStringFromResource(R.string.list_item_price),
                    coin.quoteItem?.quoteUSD?.priceUSD)

            if (!coin.logo.isNullOrEmpty())
                Picasso.with(context).load(coin.logo).into(listItemLogo)

            if (favoritesList.contains(coin.toFavoriteItem())) {
                favoritesBtn.visibility = View.INVISIBLE
            } else {
                //Without setting to visible, the viewholder is getting confused and randomly removing the button.
                favoritesBtn.visibility = View.VISIBLE
                favoritesBtn.setOnClickListener {
                    //call dao here
                    listener?.onFavoriteClicked(CoinFavoriteItem(coin.id.toInt(),
                            coin.slug,
                            coin.name,
                            coin.symbol))
                }
            }

            setOnClickListener(this@CoinListViewHolder)
        }
    }


    override fun onClick(v: View?) {
        listener?.onCoinClicked(coinItem)
    }
}