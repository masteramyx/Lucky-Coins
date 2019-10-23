package com.example.kyleamyx.luckycoins.list

import com.example.kyleamyx.luckycoins.api.LuckyCoinApiService
import com.example.kyleamyx.luckycoins.models.CoinListItem
import io.reactivex.Single
import org.koin.core.context.GlobalContext.get


class CoinListRepositoryImpl(
        val coinListService: LuckyCoinApiService = get().koin.get()
) : CoinListRepository {
    override fun getCoinList(): Single<List<CoinListItem>> {
        return coinListService.getCoinListing()
                .singleOrError()
                .map { response ->
                    response.cryptoList
                }
    }
}