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
        var artistDescription = localRepository.getArtistDescription(name)
        var cardDescription : CardArtistDescription? = null
        var broker = BrokerServiceImpl()
        var cards : List<Card> = mutableListOf()
        when {
            artistDescription != null -> {markDescriptionAsLocal(artistDescription)
            cardDescription = CardArtistDescription(artistDescription.description,"https://en.wikipedia.org/?curid="+artistDescription.id,"Wikipedia","")
            }
            /*artistDescription.let {
                    localRepository.saveDescriptionInDataBase(artistDescription!!)
                }*/
        }
        cards = broker.getInfo(name)
        return cards
    }

    private fun markDescriptionAsLocal(description: Description) {
        description.isLocallyStored = true
    }
}

