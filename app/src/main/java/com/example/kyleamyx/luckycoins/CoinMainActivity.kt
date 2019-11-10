package com.example.kyleamyx.luckycoins


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import com.example.kyleamyx.RoomSingleton
import com.example.kyleamyx.luckycoins.detail.CoinDetailController
import com.example.kyleamyx.luckycoins.list.adapter.CoinListAdapter
import com.example.kyleamyx.luckycoins.favorites.db.CoinFavoriteItem
import com.example.kyleamyx.luckycoins.models.CoinListItem
import kotlinx.android.synthetic.main.activity_coin_main.*

class CoinMainActivity : AppCompatActivity(), CoinListAdapter.CoinListListener {

    lateinit var router: Router

    /**
     * Current implementation uses BlueLineLabs Router to navigate between views and handle animations
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_main)

        router = Conductor.attachRouter(this, container, savedInstanceState)

        //todo - fix this; no alert if network shuts off mid use, should ask every 30 seconds or something
        if (!SettingUtils().checkNetworkConnectivityStatus(this))
            SettingUtils().launchPanel(this)
        else
            router.setRoot(RouterTransaction.with(CoinBaseController()))
    }


    override fun onCoinClicked(coin: CoinListItem) {
        router.pushController(RouterTransaction.with(CoinDetailController.newInstance(Bundle().apply {
            putParcelable("coinFavoriteItemItem", coin)
        })).pushChangeHandler(HorizontalChangeHandler())
                .popChangeHandler(HorizontalChangeHandler()))
    }

    override fun onFavoriteClicked(coinFavoriteItem: CoinFavoriteItem) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed() {
        if (router.backstackSize == 1) {
            finish()
        }else{
            router.popCurrentController()
        }
//        if (!router.handleBack()) {
//            finish()
//            super.onBackPressed()
//        } else {
//            title = "Crypto Price List"
//        }
    }

    override fun onResume() {
        super.onResume()
        title = "Crypto Price List"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_coin_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
