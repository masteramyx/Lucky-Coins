package com.paperspace.kyleamyx.luckycoins

import com.paperspace.kyleamyx.luckycoins.detail.CoinDetailRepository
import com.paperspace.kyleamyx.luckycoins.detail.CoinDetailRepositoryImpl
import com.paperspace.kyleamyx.luckycoins.models.CoinDetailItem
import com.paperspace.kyleamyx.luckycoins.models.CoinDetailUrlItem
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CoinDetailTests : CoinBaseTest() {

    @Mock
    private lateinit var detailRepository: CoinDetailRepository

    @Before
    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
        detailRepository = CoinDetailRepositoryImpl(luckyCoinApiService)
    }


    @Test
    fun `confirm display of detail page`() {
        mockWebServer.enqueue(response("detailResponse.json"))
        detailRepository.getCoinDetail("1")
                .test()
                .assertValue(CoinDetailItem("1", "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png",
                        "Bitcoin",
                        "BTC",
                        "abc",
                        CoinDetailUrlItem(
                                listOf("https://bitcoin.org/"),
                                listOf("https://bitcoin.org/bitcoin.pdf"),
                                emptyList(),
                                listOf("https://reddit.com/r/bitcoin"),
                                listOf("https://bitcointalk.org"),
                                emptyList(),
                                emptyList(),
                                listOf("https://blockchain.coinmarketcap.com/chain/bitcoin",
                                        "https://blockchain.info/",
                                        "https://live.blockcypher.com/btc/"),
                                listOf("https://github.com/bitcoin/")
                        )))
                .dispose()
    }
}