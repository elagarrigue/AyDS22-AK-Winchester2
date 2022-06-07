package ayds.winchester.songinfo.moredetails.model.repository

import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.entities.EmptyCard
import ayds.winchester.songinfo.moredetails.model.repository.external.BrokerService
import ayds.winchester.songinfo.moredetails.model.repository.local.LocalRepository

interface CardRepository {
    fun getCards(name: String): List<Card>
}

internal class CardRepositoryImpl(private val localRepository: LocalRepository, private val broker: BrokerService): CardRepository {

    override fun getCards(name: String): List<Card> {
        var cards = localRepository.getArtistDescription(name)

        when {
            cards.isNotEmpty() -> {markDescriptionAsLocal(cards)}
            else -> {
                cards = broker.getInfo(name)
                localRepository.saveDescriptionInDataBase(cards.filter { it !is EmptyCard })
            }
        }
        return cards
    }

    private fun markDescriptionAsLocal(cards : List<Card>) {
        cards.forEach { it.isLocallyStored = true }
    }
}

