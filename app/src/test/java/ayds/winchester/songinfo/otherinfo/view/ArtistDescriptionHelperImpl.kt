package ayds.winchester.songinfo.otherinfo.view

import ayds.winchester.songinfo.moredetails.model.entities.ArtistDescription
import ayds.winchester.songinfo.moredetails.model.entities.Description
import ayds.winchester.songinfo.moredetails.model.entities.EmptyDescription
import ayds.winchester.songinfo.moredetails.view.ArtistDescriptionHelperImpl
import org.junit.Assert
import org.junit.Test

class ArtistWikipediaArticleHelperImplTest {

    private val artistDescriptionHelper = ArtistDescriptionHelperImpl()

    @Test
    fun `given a local description it should return the description`() {
        val description: Description = ArtistDescription(
            "1",
            "<span class=\"searchmatch\">Harry</span> Edward <span class=\"searchmatch\">Styles</span> (born 1 February 1994) is an English singer, songwriter and actor. His musical career began in 2010 as a solo contestant on the British",
            true
        )

        val result = artistDescriptionHelper.getTextArtistDescription(description,"a")

        val expected =
            "<html><div width=400><font face=\"arial\">[*] <span class=\"searchmatch\">Harry</span> Edward <span class=\"searchmatch\">Styles</span> (born 1 February 1994) is an English singer, songwriter and actor. His musical career began in 2010 as a solo contestant on the British</font></div></html>"

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a non local description it should return the description`() {
        val description: Description = ArtistDescription(
            "1",
            "<span class=\"searchmatch\">Harry</span> Edward <span class=\"searchmatch\">Styles</span> (born 1 February 1994) is an English singer, songwriter and actor. His musical career began in 2010 as a solo contestant on the British",
            false
        )

        val result = artistDescriptionHelper.getTextArtistDescription(description,"a")

        val expected =
            "<html><div width=400><font face=\"arial\"><span class=\"searchmatch\">Harry</span> Edward <span class=\"searchmatch\">Styles</span> (born 1 February 1994) is an English singer, songwriter and actor. His musical career began in 2010 as a solo contestant on the British</font></div></html>"

        Assert.assertEquals(expected, result)

    }

    @Test
    fun `given a Empty description it should return the description not found`() {
        val description=EmptyDescription

        val result = artistDescriptionHelper.getTextArtistDescription(description,"a")

        val expected = "<html><div width=400><font face=\"arial\">Description not found</font></div></html>"

        Assert.assertEquals(expected, result)
    }


}