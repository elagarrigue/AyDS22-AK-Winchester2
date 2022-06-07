package ayds.winchester.songinfo.otherinfo.model.repository.external

import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.entities.CardDescription
import ayds.winchester.songinfo.moredetails.model.entities.EmptyCard
import ayds.winchester.songinfo.moredetails.model.repository.external.BrokerServiceImpl
import ayds.winchester.songinfo.moredetails.model.repository.external.Proxy
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class BrokerServiceTest {
    private val proxy1: Proxy = mockk(relaxUnitFun = true)
    private val proxy2: Proxy = mockk(relaxUnitFun = true)
    private val proxy3: Proxy = mockk(relaxUnitFun = true)

    private val broker = BrokerServiceImpl(proxy1,proxy2,proxy3)

    @Test
    fun `es posible recuperar una card de al menos un servicio`() {
        val card1 : CardDescription = mockk()
        val card2 : CardDescription = mockk()
        val card3 : CardDescription = mockk()
        val list = arrayListOf<Card>(card1,card2,card3)

        every{proxy1.getInfo("name")} returns card1
        every{proxy2.getInfo("name")} returns card2
        every{proxy3.getInfo("name")} returns card3
        val result = broker.getInfo("name")

        assertEquals(list,result)
    }
    @Test
    fun `no es posible recuperar una card de ningun servicio`() {
        val list = arrayListOf<Card>()

        every{proxy1.getInfo("name")} returns EmptyCard
        every{proxy2.getInfo("name")} returns EmptyCard
        every{proxy3.getInfo("name")} returns EmptyCard
        val result = broker.getInfo("name")

        assertEquals(list,result)
    }

}