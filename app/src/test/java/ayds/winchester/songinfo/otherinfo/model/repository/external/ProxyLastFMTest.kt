package ayds.winchester.songinfo.otherinfo.model.repository.external

import ayds.lisboa2.lastFM.LastFMArtist
import ayds.lisboa2.lastFM.LastFMService
import ayds.winchester.songinfo.moredetails.model.entities.CardDescription
import ayds.winchester.songinfo.moredetails.model.entities.EmptyCard
import ayds.winchester.songinfo.moredetails.model.entities.Source
import ayds.winchester.songinfo.moredetails.model.repository.external.ProxyLastFMImpl

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class ProxyLastFMTest {
    private val lastFMService : LastFMService = mockk(relaxUnitFun = true)
    private val proxyLastFM = ProxyLastFMImpl(lastFMService)

    @Test
    fun `el servicio puede recuperar la informacion`(){
        val info  = LastFMArtist("","","")
        val card = CardDescription(info.description,info.infoURL,Source.LASTFM, "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png",false)
        every{lastFMService.getArtist("artist")} returns info
        val result = proxyLastFM.getInfo("artist")

        Assert.assertEquals(card, result)
    }

    @Test
    fun `el servicio no puede recuperar la informacion`(){
        every{lastFMService.getArtist("artist")} returns null
        val result = proxyLastFM.getInfo("artist")

        Assert.assertEquals(EmptyCard, result)
    }

    @Test
    fun `el servicio produce una excepcion`(){
        every{lastFMService.getArtist("artist")} throws mockk<Exception>()
        val result = proxyLastFM.getInfo("artist")
        
        Assert.assertEquals(EmptyCard, result)
    }
}
