package ayds.winchester.songinfo.moredetails.model.repository

import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.entities.CardArtistDescription
import ayds.winchester.songinfo.moredetails.model.entities.Description
import ayds.winchester.songinfo.moredetails.model.entities.EmptyCard
import ayds.winchester2.wikipedia.ExternalRepository
import ayds.winchester.songinfo.moredetails.model.repository.local.LocalRepository

interface DescriptionRepository {
    fun getDescription(name: String): Card
}

internal class DescriptionRepositoryImpl(private val localRepository: LocalRepository, private val externalRepository : ExternalRepository): DescriptionRepository {

    override fun getDescription(name: String): Card {
        var artistDescription = localRepository.getArtistDescription(name)
        var cardDescription : CardArtistDescription? = null
        when {
            artistDescription != null -> {markDescriptionAsLocal(artistDescription)
            cardDescription = CardArtistDescription(artistDescription.description,"https://en.wikipedia.org/?curid="+artistDescription.id,"","")
            }
            else -> try {
                    val description = externalRepository.getArtistDescription(name)
                    cardDescription = CardArtistDescription(description.description,"https://en.wikipedia.org/?curid="+description.id,"","")

                artistDescription.let {
                        localRepository.saveDescriptionInDataBase(artistDescription!!)
                    }
                } catch (e: Exception) {
                    artistDescription = null
                }
        }
        return cardDescription ?: EmptyCard
    }

    private fun markDescriptionAsLocal(description: Description) {
        description.isLocallyStored = true
    }
}

