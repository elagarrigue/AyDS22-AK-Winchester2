package ayds.winchester.songinfo.moredetails.model.repository

import ayds.winchester.songinfo.moredetails.model.entities.Description
import ayds.winchester.songinfo.moredetails.model.entities.EmptyDescription
import ayds.winchester.songinfo.moredetails.model.repository.external.ExternalRepository
import ayds.winchester.songinfo.moredetails.model.repository.local.LocalRepository

interface DescriptionRepository {
    fun getDescription(name: String): Description
}

internal class DescriptionRepositoryImpl(private val localRepository: LocalRepository, private val externalRepository : ExternalRepository): DescriptionRepository {

    override fun getDescription(name: String): Description {
        var artistDescription = localRepository.getArtistDescription(name)

        when {
            artistDescription != null -> markDescriptionAsLocal(artistDescription)
            else -> try {
                artistDescription = externalRepository.getArtistDescription(name)
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

