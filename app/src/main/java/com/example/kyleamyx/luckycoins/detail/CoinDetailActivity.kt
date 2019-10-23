package com.example.kyleamyx.luckycoins.detail


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.example.kyleamyx.luckycoins.R
import com.example.kyleamyx.luckycoins.models.CoinListItem
import kotlinx.android.synthetic.main.activity_coin_detail.*
import kotlinx.android.synthetic.main.activity_coin_list.*

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var router: Router
    private lateinit var coinFromList: CoinListItem

    /**
     * Current implementation uses BlueLineLabs Router to navigate between views and handle animations
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)
        coinFromList = intent?.getBundleExtra("coin")?.getParcelable("coinItem") ?: CoinListItem.EMPTY
        title = coinFromList.name
        router = Conductor.attachRouter(this, containerDetail, savedInstanceState)
//        if (!router.hasRootController()) {
//            router.setRoot(RouterTransaction.with(CoinListController.newInstance()))
//        }
        router.setRoot(RouterTransaction.with(CoinDetailController.newInstance(args = intent.getBundleExtra("coin"))))
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
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

    companion object {
        fun getLaunchIntent(context: Context, coin: CoinListItem): Intent = Intent(context,
                CoinDetailActivity::class.java).putExtra("coin", Bundle().apply {
            putParcelable("coinItem", coin)
        })
    }
}
