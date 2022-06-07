package ayds.winchester.songinfo.otherinfo.model.repository.external

import ayds.winchester.songinfo.moredetails.model.entities.CardDescription
import ayds.winchester.songinfo.moredetails.model.entities.EmptyCard
import ayds.winchester.songinfo.moredetails.model.entities.Source
import ayds.winchester.songinfo.moredetails.model.repository.external.ProxyWikipediaImpl
import ayds.winchester2.wikipedia.ExternalRepository
import ayds.winchester2.wikipedia.WikipediaArticle
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class ProxyWikipediaTest {
    private val wikipediaService : ExternalRepository = mockk(relaxUnitFun = true)
    private val proxyWikipedia = ProxyWikipediaImpl(wikipediaService)

    @Test
    fun `el servicio puede recuperar la informacion`(){
        val info  = WikipediaArticle("","","https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png")
        val card = CardDescription(info.description,info.source,
            Source.WIKIPEDIA, "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png",false)
        every{wikipediaService.getArtistDescription("artist")} returns info
        val result = proxyWikipedia.getInfo("artist")

        Assert.assertEquals(card, result)
    }


    @Test
    fun `el servicio produce una excepcion`() {
        every { wikipediaService.getArtistDescription("artist") } throws mockk<Exception>()
        val result = proxyWikipedia.getInfo("artist")

        Assert.assertEquals(EmptyCard, result)
    }
}