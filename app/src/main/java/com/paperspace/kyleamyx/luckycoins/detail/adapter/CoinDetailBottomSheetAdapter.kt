package com.paperspace.kyleamyx.luckycoins.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paperspace.kyleamyx.luckycoins.R
import com.paperspace.kyleamyx.luckycoins.favorites.db.CoinFavoriteItem
import com.paperspace.kyleamyx.luckycoins.models.CoinDetailUrlItem
import com.paperspace.kyleamyx.luckycoins.models.CoinListItem

class CoinDetailBottomSheetAdapter(context: Context) : RecyclerView.Adapter<CoinDetailBottomSheetViewHolder>() {

    private lateinit var bottomSheetUrlItem: CoinDetailUrlItem
    private var urlList = mutableMapOf<String, List<String>>()
    private val inflater: LayoutInflater = LayoutInflater.from(context)


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
        TODO("crash happening here - INDEX OUT OF BOUNDS")
        val t = urlList.getOrPut(list[position], { listOf("not here") })
        if (t.isNotEmpty() && !t.contains("not here"))
            holder.bindView(list[position], t)
    }

    fun addItems(urls: MutableMap<String, List<String>>) {
        urlList = urls.filter {
            it.value.isNotEmpty().also {bool ->
                if(!bool){
                    list.remove(it.key)
                }
            }
        } as MutableMap<String, List<String>>
        if (urlList.isNotEmpty()) {
            notifyDataSetChanged()
        }
    }
//
//    fun getItem(position: Int): CoinListItem {
//        return coinList[position]
//    }

    companion object {
        val list = mutableListOf("website", "technicalDoc", "twitter", "reddit", "explorer")
    }
}