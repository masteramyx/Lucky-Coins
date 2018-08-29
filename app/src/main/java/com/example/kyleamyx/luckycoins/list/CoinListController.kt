package com.example.kyleamyx.luckycoins.list

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.example.kyleamyx.luckycoins.R
import com.example.kyleamyx.luckycoins.models.CoinListItem

/**
 * Created by kyleamyx on 6/23/18.
 */
class CoinListController : Controller(), View.OnClickListener {


    interface onCoinClicked {
        fun onItemClicked(coin: CoinListItem)
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


     var listener: onCoinClicked? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.coin_list_controller, container, false)
        val recycler : RecyclerView = view.findViewById(R.id.listRecycler)
        recycler.layoutManager = LinearLayoutManager(activity)

        return view
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        listener = activity as onCoinClicked
        //getListings
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        listener = null
    }


    companion object {
        @JvmStatic
        fun newInstance(): CoinListController = CoinListController()
    }


}