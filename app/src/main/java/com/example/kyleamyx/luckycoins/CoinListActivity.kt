package com.example.kyleamyx.luckycoins


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import com.bluelinelabs.conductor.support.RouterPagerAdapter
import com.example.kyleamyx.RoomSingleton
import com.example.kyleamyx.luckycoins.favorites.CoinFavoriteController
import com.example.kyleamyx.luckycoins.list.CoinListController
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_coin_list.*

class CoinListActivity : AppCompatActivity() {

    private lateinit var router: Router

    /**
     * Current implementation uses BlueLineLabs Router to navigate between views and handle animations
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_list)
        val tabs: TabLayout = tabs
        //todo - How to Set up with ViewPager
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

        RoomSingleton.initDB(this)

        println("Router: ${router.containerId}")
        //todo - remove this shit if it doesn't work
        viewPager.adapter = TabAdapter()

        tabs.setupWithViewPager(viewPager)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
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

    inner class TabAdapter : RouterPagerAdapter(CoinListController.newInstance()) {
        override fun configureRouter(router: Router, position: Int) {
            when (position) {
                0 -> this@CoinListActivity.router.setRoot(RouterTransaction.with(CoinListController.newInstance()))
//                    router.pushController(RouterTransaction.with(CoinListController())
//                            .pushChangeHandler(HorizontalChangeHandler())
//                            .popChangeHandler(HorizontalChangeHandler()))
                1 -> this@CoinListActivity.router.pushController(RouterTransaction.with(CoinFavoriteController())
                        .pushChangeHandler(HorizontalChangeHandler())
                        .popChangeHandler(HorizontalChangeHandler()))
            }
        }

        override fun getCount(): Int = 2
    }

}
