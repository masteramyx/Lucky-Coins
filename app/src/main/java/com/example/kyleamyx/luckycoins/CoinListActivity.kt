package com.example.kyleamyx.luckycoins


import android.content.Intent
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import com.example.kyleamyx.luckycoins.detail.CoinDetailActivity
import com.example.kyleamyx.luckycoins.favorites.CoinFavoriteController
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
        val tabs: TabLayout = tabs
        tabs.addTab(tabs.newTab().setText("List"))
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.text == "List") {

                    router.popCurrentController()
                } else {
                    Toast.makeText(applicationContext, "FAVORITE CLICKED!", Toast.LENGTH_SHORT).show()
                    router.pushController(RouterTransaction.with(CoinFavoriteController())
                            .pushChangeHandler(HorizontalChangeHandler())
                            .popChangeHandler(HorizontalChangeHandler()))
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                //refresh list
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // no op?
            }
        })
        tabs.addTab(tabs.newTab().setText("Favorites"))

        router = Conductor.attachRouter(this, container, savedInstanceState)
        if (!SettingUtils().checkNetworkConnectivityStatus(this))
            SettingUtils().launchPanel(this)
        else
            router.setRoot(RouterTransaction.with(CoinListController.newInstance()))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        router.setRoot(RouterTransaction.with(CoinListController.newInstance()))
    }


    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        } else {
            title = "Crypto Price List"
        }
    }

    override fun onResume() {
        super.onResume()
        title = "Crypto Price List"
    }

    /**
     *Upon Item click the router pushes a new {@link CoinDetailController} to the backstack and
     * handles the view animation
     */
    override fun onItemClicked(coin: CoinListItem) {
        Toast.makeText(this, "Heading into the Detail Activity", Toast.LENGTH_SHORT).show()
        startActivity(CoinDetailActivity.getLaunchIntent(this, coin))
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
