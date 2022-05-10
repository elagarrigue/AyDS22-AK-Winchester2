package ayds.winchester.songinfo.moredetails.home.model.repository

import ayds.winchester.songinfo.moredetails.home.model.entities.Description
import ayds.winchester.songinfo.moredetails.home.model.entities.EmptyDescription
import ayds.winchester.songinfo.moredetails.home.model.repository.external.ExternalRepository
import ayds.winchester.songinfo.moredetails.home.model.repository.local.LocalRepository

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
                    when{
                        it.isSavedDescription() -> {
                            localRepository.updateArtistTerm(it.id)
                        }
                        else -> {
                            localRepository.saveDescriptionInDataBase(artistDescription!!)
                        }
                    }
                }
                } catch (e: Exception) {
                    artistDescription = null
                }
        }
        return artistDescription ?: EmptyDescription
    }

    private fun Description.isSavedDescription() = localRepository.getArtistDescriptionById(id) != null

    private fun markDescriptionAsLocal(description: Description) {
        description.isLocallyStored = true
    }
}

