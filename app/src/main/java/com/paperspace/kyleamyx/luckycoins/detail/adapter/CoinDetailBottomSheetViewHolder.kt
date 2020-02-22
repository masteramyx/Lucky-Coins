package com.paperspace.kyleamyx.luckycoins.detail.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.karakum.helpers.CustomTabHelper
import kotlinx.android.synthetic.main.coin_detail_url_item.view.*

class CoinDetailBottomSheetViewHolder(bottomSheetView: View)
    : RecyclerView.ViewHolder(bottomSheetView), View.OnClickListener {

    override fun onClick(v: View) {
        when(v){
            v.detailUrlAddress -> {
                CustomTabHelper.launchCustomTab(v.detailUrlAddress.text.toString(), v.context)
            }
        }
    }

    fun bindView(title: String, urls: List<String>) {
        itemView.apply {
            if (urls.isNotEmpty()) {
            detailUrlTitle.text = title
                if (urls.size > 1) {
                    // Create a vertical list of URL's within the TextView
                    detailUrlAddress.text = urls.reduce { acc, s -> acc + "\n" + s }
                } else {
                    detailUrlAddress.text = urls[0]
                }
                detailUrlAddress.setOnClickListener(this@CoinDetailBottomSheetViewHolder)
            }
        }
    }
}