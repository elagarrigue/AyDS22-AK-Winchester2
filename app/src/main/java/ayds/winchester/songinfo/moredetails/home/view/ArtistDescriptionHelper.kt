package ayds.winchester.songinfo.moredetails.home.view

import ayds.winchester.songinfo.moredetails.home.model.entities.Description

interface ArtistDescriptionHelper {
    fun getTextArtistDescription(description: Description): String
}
internal class ArtistDescriptionHelperImpl() : ArtistDescriptionHelper {
    override fun getTextArtistDescription (description: Description) : String{
        return ""
    }
}