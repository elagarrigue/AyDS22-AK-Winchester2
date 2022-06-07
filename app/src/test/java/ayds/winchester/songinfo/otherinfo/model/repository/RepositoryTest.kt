package ayds.winchester.songinfo.otherinfo.model.repository

import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.entities.CardDescription
import ayds.winchester.songinfo.moredetails.model.entities.Source
import ayds.winchester.songinfo.moredetails.model.repository.CardRepositoryImpl
import ayds.winchester.songinfo.moredetails.model.repository.external.BrokerService
import ayds.winchester.songinfo.moredetails.model.repository.local.LocalRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test

class RepositoryTest {
    private val localRepository: LocalRepository = mockk(relaxUnitFun = true)
    private val broker : BrokerService = mockk(relaxUnitFun = true)

    private val descriptionRepository: CardRepositoryImpl by lazy {
        CardRepositoryImpl(localRepository, broker)
    }

    @Test
    fun `given existing cards should return cards and mark it as local`() {
        val cards = listOf(
            CardDescription("", "1", Source.WIKIPEDIA, "" )
        )
        every { localRepository.getArtistDescription("artist") } returns cards

        val result = descriptionRepository.getCards("artist")

        Assert.assertEquals(cards, result)
        Assert.assertTrue(cards.all { it.isLocallyStored })
    }

    @Test
    fun `given non existing cards should get the description and store it`() {
        var cards: MutableList<Card> = mutableListOf()
        val card : Card = CardDescription("", "1", Source.WIKIPEDIA, "" )
        cards.add(card)
        every { localRepository.getArtistDescription("artist") } returns emptyList()
        every { broker.getInfo("artist") } returns cards

        val result = descriptionRepository.getCards("artist")

        Assert.assertEquals(cards, result)
        Assert.assertFalse(cards.last().isLocallyStored)
        verify { localRepository.saveDescriptionInDataBase(cards) }
    }

    @Test
    fun `given non existing description should return empty list`() {
        every { localRepository.getArtistDescription("artist") } returns emptyList()
        every { broker.getInfo("artist") } returns emptyList()

        val result = descriptionRepository.getCards("artist")

        Assert.assertEquals(emptyList<Card>(), result)
    }
}