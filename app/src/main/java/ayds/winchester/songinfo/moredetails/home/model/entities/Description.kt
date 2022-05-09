package ayds.winchester.songinfo.moredetails.home.model.entities

interface Description {
    val id: String
    var description: String
    var isLocallyStored : Boolean
}


data class ArtistDescription(
    override val id: String,
    override var description: String,
    override var isLocallyStored: Boolean = false
) : Description

object EmptyDescription : Description {
    override val id: String = ""
    override var description: String = "Description not found"
    override var isLocallyStored: Boolean = false
}
