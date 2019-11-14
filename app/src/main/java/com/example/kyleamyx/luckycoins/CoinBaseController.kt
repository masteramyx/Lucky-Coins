package com.example.kyleamyx.luckycoins

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.support.RouterPagerAdapter
import com.example.kyleamyx.luckycoins.favorites.CoinFavoriteController
import com.example.kyleamyx.luckycoins.list.CoinListController
import kotlinx.android.synthetic.main.coin_base_controller.view.*

class CoinBaseController : Controller() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.coin_base_controller, container, false).apply {
            viewPager.adapter = TabAdapter(this)
            tabs.setupWithViewPager(viewPager)
        }
    }

    /**
     * This TabAdapter has no use outside the base controller and should not be messed with, making private-inner to
     * avoid any mixups
     */
    private inner class TabAdapter(val tabLayoutView: View) : RouterPagerAdapter(this) {
        override fun configureRouter(router: Router, position: Int) {
            when (position) {
                0 -> router.pushController(RouterTransaction.with(CoinListController.newInstance()))
                1 -> router.pushController(RouterTransaction.with(CoinFavoriteController.newInstance()))
            }

        }

        override fun getCount(): Int = 2

        override fun getPageTitle(position: Int): CharSequence? {
            return if (position == 0)
                tabLayoutView.getStringFromResource(R.string.tab_list)
            else
                tabLayoutView.getStringFromResource(R.string.tab_favorite)
        }
    }
}