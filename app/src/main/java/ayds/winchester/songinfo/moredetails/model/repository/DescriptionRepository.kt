package ayds.winchester.songinfo.moredetails.model.repository

import ayds.winchester.songinfo.moredetails.model.entities.ArtistDescription
import ayds.winchester.songinfo.moredetails.model.entities.Description
import ayds.winchester.songinfo.moredetails.model.entities.EmptyDescription
import ayds.winchester2.wikipedia.ExternalRepository
import ayds.winchester.songinfo.moredetails.model.repository.local.LocalRepository


interface DescriptionRepository {
    fun getDescription(name: String): Description
}

internal class DescriptionRepositoryImpl(private val localRepository: LocalRepository, private val externalRepository : ayds.winchester2.wikipedia.ExternalRepository): DescriptionRepository {

    override fun getDescription(name: String): Description {
        var artistDescription = localRepository.getArtistDescription(name)

        when {
            artistDescription != null -> markDescriptionAsLocal(artistDescription)
            else -> try {

                    val description = externalRepository.getArtistDescription(name)
                    artistDescription = ArtistDescription(description.id,description.description)

                artistDescription.let {
                        localRepository.saveDescriptionInDataBase(artistDescription!!)
                    }
                } catch (e: Exception) {
                    artistDescription = null
                }
        }
        return artistDescription ?: EmptyDescription
    }

    private fun markDescriptionAsLocal(description: Description) {
        description.isLocallyStored = true
    }
}

