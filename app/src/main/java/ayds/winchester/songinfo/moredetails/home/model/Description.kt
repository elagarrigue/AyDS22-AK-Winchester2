package ayds.winchester.songinfo.moredetails.home.model

import com.google.gson.JsonElement

interface Description {
    val id: String
    val description: String
}

data class ArtistDescription(
    override val id: String,
    override val description: String,
) : Description
