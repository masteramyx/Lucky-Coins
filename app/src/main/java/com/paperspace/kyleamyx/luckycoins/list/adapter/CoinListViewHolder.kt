package com.paperspace.kyleamyx.luckycoins.list.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.paperspace.kyleamyx.SharedPrefHelper
import com.paperspace.kyleamyx.luckycoins.R
import com.paperspace.kyleamyx.luckycoins.favorites.CoinFavoriteRepository
import com.paperspace.kyleamyx.luckycoins.getStringFromResource
import com.paperspace.kyleamyx.luckycoins.favorites.db.CoinFavoriteItem
import com.paperspace.kyleamyx.luckycoins.models.CoinListItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.coin_list_item.view.*
import org.koin.core.context.GlobalContext.get

/**
 * Created by kyleamyx on 6/23/18.
 */
class CoinListViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    var listener: CoinListAdapter.CoinListListener? = null
    val sharedPrefHelper: SharedPrefHelper.SharedPref = get().koin.get()

    private val favoritesRepository: CoinFavoriteRepository = get().koin.get()
    //todo - don't do blocking, find a better way to get favorites list here...maybe pass it in or inject?
    private val favoritesList = favoritesRepository.getFavorites().blockingGet()

    private lateinit var coinItem: CoinListItem

    fun bindView(coin: CoinListItem) {
        coinItem = coin
        itemView.apply {
            coinName.text = coin.name
            coinSymbol.text = coin.symbol
            coinListPrice.text =
                    String.format(itemView.getStringFromResource(R.string.list_item_price), coin.quote.USD.price)

            if (!coin.logo.isNullOrEmpty())
                Picasso.with(context).load(coin.logo).into(listItemLogo)

            // Hide favorites btn if it is in Favorites DB
            if (favoritesList.contains(coin.toFavoriteItem())) {
                favoritesBtn.visibility = View.INVISIBLE
            } else {
                //Without setting to visible, the viewholder is getting confused and randomly removing the button.
                favoritesBtn.visibility = View.VISIBLE
            }

            // OnClick, save to DB and hide the button
            favoritesBtn.setOnClickListener {
                visibility = View.INVISIBLE
                //call dao here
                listener?.onFavoriteClicked(CoinFavoriteItem(coin.id.toInt(),
                        coin.slug,
                        coin.name,
                        coin.symbol))
            }

            setOnClickListener(this@CoinListViewHolder)

            when (sharedPrefHelper.getCoinPercentChangeTimePreference()) {
                "1h" -> {
                    when (coin.quote.USD.percent_change_1h > 0) {
                        true -> coinArrow.setImageDrawable(context.getDrawable(R.drawable.green_up))

                        false -> coinArrow.setImageDrawable(context.getDrawable(R.drawable.red_down))
                    }
                }

                "24h" -> {
                    when (coin.quote.USD.percent_change_24h > 0) {
                        true -> coinArrow.setImageDrawable(context.getDrawable(R.drawable.green_up))

                        false -> coinArrow.setImageDrawable(context.getDrawable(R.drawable.red_down))
                    }
                }

                "7d" -> {
                    when (coin.quote.USD.percent_change_7d > 0) {
                        true -> coinArrow.setImageDrawable(context.getDrawable(R.drawable.green_up))

                        false -> coinArrow.setImageDrawable(context.getDrawable(R.drawable.red_down))
                    }
                }

            }

        }
    }


    override fun onClick(v: View?) {
        listener?.onCoinClicked(coinItem)
    }
}