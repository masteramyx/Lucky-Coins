package com.paperspace.kyleamyx.luckycoins.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paperspace.kyleamyx.luckycoins.R
import com.paperspace.kyleamyx.luckycoins.favorites.db.CoinFavoriteItem
import com.paperspace.kyleamyx.luckycoins.models.CoinListItem

class CoinDetailBottomSheetAdapter(context: Context) : RecyclerView.Adapter<CoinDetailBottomSheetViewHolder>() {

    private var urlList = mutableMapOf<String, List<String>>()
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    //private val list = mutableListOf("website", "technicalDoc", "twitter", "reddit", "explorer")
    private lateinit var list : MutableList<String>

    interface CoinDetailBottomSheetListener {
        fun onCoinClicked(coin: CoinListItem)
        fun onFavoriteClicked(coinFavoriteItem: CoinFavoriteItem)
    }


    override fun getItemCount(): Int {
        return urlList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinDetailBottomSheetViewHolder {
        val view = inflater.inflate(R.layout.coin_detail_url_item, parent, false)
        return CoinDetailBottomSheetViewHolder(view)
    }


    override fun onBindViewHolder(holder: CoinDetailBottomSheetViewHolder, position: Int) {
        val urlListItem = urlList.getOrPut(list[position], { listOf("not here") })
        if (urlListItem.isNotEmpty() && !urlListItem.contains("not here"))
            holder.bindView(list[position], urlListItem)
    }

    fun addItems(urls: MutableMap<String, List<String>>, titleList : MutableList<String>) {
        list = titleList
        filterUrlList(urls)
    }

    /**
     * Not all coins will have links to the web sections provided from API, remove the section labels which are not
     * available
     */
    private fun filterUrlList(urls: MutableMap<String, List<String>>) {
        urlList = urls.filter {
            it.value.isNotEmpty().also { bool ->
                if (!bool) {
                    list.remove(it.key)
                }
            }
        } as MutableMap<String, List<String>>
        if (urlList.isNotEmpty()) {
            notifyDataSetChanged()
        }
    }
}