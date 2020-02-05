package com.paperspace.kyleamyx.luckycoins

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.paperspace.kyleamyx.RxBus
import com.paperspace.kyleamyx.RxSettingsEvent
import com.paperspace.kyleamyx.SharedPrefHelper
import kotlinx.android.synthetic.main.activity_coin_settings.*
import org.koin.android.ext.android.inject

class CoinSettingsActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var timeFrame: String
    private val sharedPrefHelper: SharedPrefHelper.SharedPref by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_settings)
        retrieveCurrentSelection()
        applyBtn.setOnClickListener(this)
        coinTimeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == hour1.id) {
                timeFrame = "1h"
            }
            if (checkedId == hour24.id) {
                timeFrame = "24h"
            }
            if (checkedId == day7.id) {
                timeFrame = "7d"
            }
        }
    }

    private fun retrieveCurrentSelection() {
        val currentSelection = when (sharedPrefHelper.getCoinPercentChangeTimePreference()) {
            "1h" -> hour1.id
            "24h" -> hour24.id
            "7d" -> day7.id
            else -> null
        }
        coinTimeRadioGroup.check(requireNotNull(currentSelection))
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.applyBtn -> {
                sharedPrefHelper.setCoinPercentChangeTimePreference(timeFrame)
                RxBus.publish(RxSettingsEvent.TimeSettingChange(timeFrame))
                finish()

            }
        }
    }
}