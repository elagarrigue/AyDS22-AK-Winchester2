package ayds.winchester.songinfo.otherinfo.model.repository.external

import ayds.ak1.newyorktimes.article.external.NYArticle
import ayds.ak1.newyorktimes.article.external.NYInfoService
import ayds.winchester.songinfo.moredetails.model.entities.CardDescription
import ayds.winchester.songinfo.moredetails.model.entities.EmptyCard
import ayds.winchester.songinfo.moredetails.model.entities.Source
import ayds.winchester.songinfo.moredetails.model.repository.external.ProxyTimesImpl
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test


class ProxyTimesTest {
    private val nyService : NYInfoService = mockk(relaxUnitFun = true)
    private val proxyTimes = ProxyTimesImpl(nyService)

    @Test
    fun `el servicio puede recuperar la informacion`(){
        val info  = NYArticle("","","","")
        val card = CardDescription(info.description,info.infoURL,
            Source.NEWYORKTIMES, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVioI832nuYIXqzySD8cOXRZEcdlAj3KfxA62UEC4FhrHVe0f7oZXp3_mSFG7nIcUKhg&usqp=CAU",false)
        every{nyService.getArtistInfo("artist")} returns info
        val result = proxyTimes.getInfo("artist")

        Assert.assertEquals(card, result)
    }

    @Test
    fun `el servicio no puede recuperar la informacion`(){
        every{nyService.getArtistInfo("artist")} returns null
        val result = proxyTimes.getInfo("artist")

        Assert.assertEquals(EmptyCard, result)
    }

    @Test
    fun `el servicio produce una excepcion`() {
        every { nyService.getArtistInfo("artist") } throws mockk<Exception>()
        val result = proxyTimes.getInfo("artist")

        Assert.assertEquals(EmptyCard, result)
    }
}