package ayds.winchester.songinfo.moredetails.model.repository

import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.entities.EmptyCard
import ayds.winchester.songinfo.moredetails.model.repository.external.BrokerService
import ayds.winchester.songinfo.moredetails.model.repository.local.LocalRepository

interface DescriptionRepository {
    fun getDescription(name: String): List<Card>
}

internal class DescriptionRepositoryImpl(private val localRepository: LocalRepository, private val broker: BrokerService): DescriptionRepository {

    override fun getDescription(name: String): List<Card> {
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
        for(i in cards.indices)
            cards[i].isLocallyStored = true
    }
}

