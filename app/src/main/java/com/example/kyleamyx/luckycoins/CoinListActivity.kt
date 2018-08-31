package com.example.kyleamyx.luckycoins


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import com.example.kyleamyx.luckycoins.detail.CoinDetailController
import com.example.kyleamyx.luckycoins.list.CoinListController
import com.example.kyleamyx.luckycoins.models.CoinListItem
import kotlinx.android.synthetic.main.activity_coin_list.*

class CoinListActivity : AppCompatActivity(), CoinListController.OnCoinClicked {

    private lateinit var router: Router

    /**
     * Current implementation uses BlueLineLabs Router to navigate between views and handle animations
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_list)
        setSupportActionBar(toolbar)
        title = "Crypto Price List"
        router = Conductor.attachRouter(this, container, savedInstanceState)
//        if (!router.hasRootController()) {
//            router.setRoot(RouterTransaction.with(CoinListController.newInstance()))
//        }
        router.setRoot(RouterTransaction.with(CoinListController.newInstance()))
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        } else {
            title = "Crypto Price List"
        }
    }

    /**
     *Upon Item click the router pushs a new {@link CoinDetailController} to the backstack and
     * handles the view animation
     */
    override fun onItemClicked(coin: CoinListItem) {
        title = coin.name

        router.pushController(RouterTransaction.with(CoinDetailController())
                .pushChangeHandler(HorizontalChangeHandler())
                .popChangeHandler(HorizontalChangeHandler()))
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
