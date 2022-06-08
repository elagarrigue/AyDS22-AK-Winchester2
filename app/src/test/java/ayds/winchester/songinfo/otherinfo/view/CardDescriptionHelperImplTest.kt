package ayds.winchester.songinfo.otherinfo.view

import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.entities.CardDescription
import ayds.winchester.songinfo.moredetails.model.entities.EmptyCard
import ayds.winchester.songinfo.moredetails.model.entities.Source
import ayds.winchester.songinfo.moredetails.view.CardDescriptionHelperImpl
import org.junit.Assert
import org.junit.Test

class CardDescriptionHelperImplTest {

    private val cardDescriptionHelper = CardDescriptionHelperImpl()

    @Test
    fun `given a local card it should return the description`() {
        val card: Card = CardDescription(
            "<span class=\"searchmatch\">Harry</span> Edward <span class=\"searchmatch\">Styles</span> (born 1 February 1994) is an English singer, songwriter and actor. His musical career began in 2010 as a solo contestant on the British",
            "",
            Source.WIKIPEDIA,
            "",
            true
        )

        val result = cardDescriptionHelper.getTextCard(card,"a")

        val expected =
            "<html><div width=400><font face=\"arial\">[*] <span class=\"searchmatch\">Harry</span> Edward <span class=\"searchmatch\">Styles</span> (born 1 February 1994) is an English singer, songwriter and actor. His musical career began in 2010 as a solo contestant on the British</font></div></html>"

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a non local card it should return the description`() {
        val card: Card = CardDescription(
            "<span class=\"searchmatch\">Harry</span> Edward <span class=\"searchmatch\">Styles</span> (born 1 February 1994) is an English singer, songwriter and actor. His musical career began in 2010 as a solo contestant on the British",
            "",
            Source.WIKIPEDIA,
            "",
            false
        )

        val result = cardDescriptionHelper.getTextCard(card,"a")

        val expected =
            "<html><div width=400><font face=\"arial\"><span class=\"searchmatch\">Harry</span> Edward <span class=\"searchmatch\">Styles</span> (born 1 February 1994) is an English singer, songwriter and actor. His musical career began in 2010 as a solo contestant on the British</font></div></html>"

        Assert.assertEquals(expected, result)

    }

    @Test
    fun `given a Empty description it should return the description not found`() {
        val card=EmptyCard

        val result = cardDescriptionHelper.getTextCard(card,"a")

        val expected = "<html><div width=400><font face=\"arial\">Description not found</font></div></html>"

        Assert.assertEquals(expected, result)
    }
}