package ayds.winchester.songinfo.moredetails.model.repository

import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.entities.CardArtistDescription
import ayds.winchester.songinfo.moredetails.model.entities.Description
import ayds.winchester.songinfo.moredetails.model.entities.EmptyCard
import ayds.winchester2.wikipedia.ExternalRepository
import ayds.winchester.songinfo.moredetails.model.repository.local.LocalRepository
import ayds.winchester.songinfo.moredetails.model.repository.external.BrokerServiceImpl

interface DescriptionRepository {
    fun getDescription(name: String): List<Card>
}

internal class DescriptionRepositoryImpl(private val localRepository: LocalRepository, private val externalRepository : ExternalRepository): DescriptionRepository {
    override fun getDescription(name: String): List<Card> {
        var cards = localRepository.getArtistDescription(name)
        var broker = BrokerServiceImpl()

        when {
            cards.isNotEmpty() -> {markDescriptionAsLocal(cards)}
            else -> {
                cards = broker.getInfo(name)
                localRepository.saveDescriptionInDataBase(cards)
                println("---------------------------------")
            }
        }
        return cards
    }

    private fun markDescriptionAsLocal(cards : List<Card>) {
        for(i in cards.indices)
            cards[i].isLocallyStored = true
    }
}

