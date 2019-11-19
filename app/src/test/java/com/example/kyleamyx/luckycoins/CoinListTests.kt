package com.example.kyleamyx.luckycoins

import com.example.kyleamyx.luckycoins.list.CoinListRepository
import com.example.kyleamyx.luckycoins.list.CoinListRepositoryImpl
import com.example.kyleamyx.luckycoins.list.db.CoinListDao
import org.junit.Test
import org.koin.core.context.GlobalContext.get
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CoinListTests : CoinBaseTest() {

    @Mock
    private lateinit var listRepository: CoinListRepository

    private val db: CoinDb = get().koin.get()
    private val coinListDao: CoinListDao = get().koin.get()


    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
        listRepository = CoinListRepositoryImpl(luckyCoinApiService, coinListDao)
    }


    @Test
    fun `confirm fetching of list no images`() {
        mockWebServer.enqueue(response("listResponse.json"))
        listRepository.getListNoImages()
                .test()
                .assertOf {
                    it.assertValueCount(3)
                }
                .dispose()

    }


}