package com.example.kyleamyx.luckycoins.detail.adapter

import android.view.View
import android.widget.ImageView
import com.example.kyleamyx.luckycoins.R
import com.example.kyleamyx.luckycoins.models.CoinDetailItem
import com.squareup.picasso.Picasso

/**
 * Created by kyleamyx on 8/31/18.
 */
class CoinDetailViewHolder(val itemView: View, val detailItem: CoinDetailItem) : View.OnClickListener {

    fun bindView(coin: CoinDetailItem = detailItem) {
        Picasso.with(itemView.context).load(coin.logo)
                .resize(50, 50)
                .into(itemView.findViewById<ImageView>(R.id.coinHeroIv))
    }

    override fun onClick(v: View?) {
        //no op yet
    }
}
