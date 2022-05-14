package ayds.winchester.songinfo.moredetails.model.repository.external

import ayds.winchester.songinfo.moredetails.model.entities.Description

interface ExternalRepository {
    fun getArtistDescription(artistName: String): Description
}

internal class ExternalRepositoryImpl(
    private var wikipediaAPI: WikipediaAPI,
    private var wikipediaToDescriptionResolver: WikipediaToDescriptionResolver
) : ExternalRepository {

    override fun getArtistDescription(artistName: String): Description {
        val queryWikipediaSearch = wikipediaAPI.getArtistInfo(artistName).execute()

        return wikipediaToDescriptionResolver.getDescriptionFromExternalData(queryWikipediaSearch)
    }
}