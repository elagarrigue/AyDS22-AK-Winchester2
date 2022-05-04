package ayds.winchester.songinfo.moredetails.home.model.repository

import ayds.winchester.songinfo.moredetails.home.model.entities.ArtistDescription
import ayds.winchester.songinfo.moredetails.home.model.entities.Description
import ayds.winchester.songinfo.moredetails.home.model.entities.EmptyDescription
import ayds.winchester.songinfo.moredetails.home.model.repository.external.ExternalRepository
import ayds.winchester.songinfo.moredetails.home.model.repository.local.LocalRepository
import ayds.winchester.songinfo.moredetails.home.view.OtherInfoWindow

interface DescriptionRepository {
    fun getDescription(term: String): Description
}

internal class DescriptionRepositoryImpl(otherInfoView : OtherInfoWindow): DescriptionRepository {

    private val localRepository = LocalRepository(otherInfoView)
    private val externalRepository = ExternalRepository()

    override fun getDescription(name: String): Description {
        var artistDescription = localRepository.getArtistDescription(name)

        when {
            artistDescription != null -> markDescriptionAsLocal(artistDescription)
            else -> {
                try {
                    artistDescription = externalRepository.getArtistDescription(name)

                    artistDescription.let {
                   /*     when {
                         //   it.isSavedDescription() -> localRepository.updateSongTerm(name, it.id)
                            else -> localRepository.insertSong(name, it)
                        }*/
                        localRepository.saveDescriptionInDataBase(artistDescription!!.description)
                    }

                } catch (e: Exception) {
                    artistDescription = null
                }
            }
        }

        return artistDescription ?: EmptyDescription
    }

    private fun Description.isSavedDescription() = localRepository.getArtistDescriptionById(id) != null

    private fun markDescriptionAsLocal(description: Description) {
        description.isLocallyStored = true
    }
}

