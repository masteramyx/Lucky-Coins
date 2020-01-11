package com.paperspace.kyleamyx.luckycoins.api.response

import com.paperspace.kyleamyx.luckycoins.models.CoinListItem
import kotlinx.serialization.Serializable

/**
 * Created by kyleamyx on 6/23/18.
 */
@Serializable
data class CoinListResponse(val data: List<CoinListItem>)