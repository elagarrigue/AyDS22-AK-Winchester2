package ayds.winchester.songinfo.moredetails.model.entities

const val URL_IMAGE_NOT_FOUND =
    "https://alfabetajuega.com/hero/2018/10/167687.alfabetajuega-problemas-tecnicos.jpg"

interface Card{
    val description: String
    val infoUrl: String
    val source : Source
    val sourceLogoUrl : String
    var isLocallyStored: Boolean
}

data class CardDescription (
    override val description: String,
    override val infoUrl: String,
    override val source: Source,
    override val sourceLogoUrl: String,
    override var isLocallyStored: Boolean = false
): Card

object EmptyCard: Card {
    override val description = "Description not found"
    override val infoUrl = ""
    override val source: Source = Source.NOSOURCE
    override val sourceLogoUrl= URL_IMAGE_NOT_FOUND
    override var isLocallyStored = false
}

class CardUI(
    override val description: String,
    override val infoUrl: String,
    override val source: Source,
    override val sourceLogoUrl: String,
    override var isLocallyStored : Boolean = false
) : Card {
    var isEnabled: Boolean = false
}
