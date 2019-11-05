package com.example.kyleamyx.luckycoins

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import com.bluelinelabs.conductor.support.RouterPagerAdapter
import com.example.kyleamyx.luckycoins.favorites.CoinFavoriteController
import com.example.kyleamyx.luckycoins.list.CoinListController
import kotlinx.android.synthetic.main.activity_coin_list.view.*
import kotlinx.android.synthetic.main.coin_base_controller.view.*

class CoinBaseController : Controller() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.coin_base_controller, container, false).apply {
            viewPager.adapter = TabAdapter()
            tabs.setupWithViewPager(viewPager)
        }
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
    }


    inner class TabAdapter : RouterPagerAdapter(this) {
        override fun configureRouter(router: Router, position: Int) {
            router.setPopsLastView(true)
            when (position) {
                0 -> router.pushController(RouterTransaction.with(CoinListController.newInstance()))
                1 -> router.pushController(RouterTransaction.with(CoinFavoriteController.newInstance()))
            }

        }

        override fun getCount(): Int = 2

        override fun getPageTitle(position: Int): CharSequence? {
            return if (position == 0)
                "List"
            else
                "Favorites"
        }
    }
}