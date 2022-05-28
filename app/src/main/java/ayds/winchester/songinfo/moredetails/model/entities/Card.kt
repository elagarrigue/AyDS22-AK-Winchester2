package ayds.winchester.songinfo.moredetails.model.entities

interface Card{
    val description: String
    val infoUrl: String
    val source : String
    val sourceLogoUrl : String
    val isLocallyStored: Boolean
}

data class CardArtistDescription (
    override val description: String,
    override val infoUrl: String,
    override val source: String,
    override val sourceLogoUrl: String,
    override var isLocallyStored: Boolean = false
): Card

object EmptyCard: Card {
    override val description = "Description not found"
    override val infoUrl = ""
    override val source = ""
    override val sourceLogoUrl= ""
    override var isLocallyStored = false
}
