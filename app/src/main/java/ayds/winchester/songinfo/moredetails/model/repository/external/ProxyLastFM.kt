package ayds.winchester.songinfo.moredetails.model.repository.external

import ayds.lisboa2.lastFM.LastFMArtist
import ayds.lisboa2.lastFM.LastFMInjector.lastFMService
import ayds.lisboa2.lastFM.LastFMService
import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.entities.CardArtistDescription
import ayds.winchester.songinfo.moredetails.model.entities.EmptyCard
import ayds.winchester.songinfo.moredetails.model.entities.Source

interface ProxyLastFM {
    fun getInfo (name : String) : Card
}


internal class ProxyLastFMImpl : ProxyLastFM {

    override fun getInfo (name : String) : Card {
        var cardDescriptionLastFM: CardArtistDescription? = null
        val descriptionLastFM: LastFMArtist

        try {

            descriptionLastFM = lastFMService.getArtist(name)!!

            cardDescriptionLastFM = CardArtistDescription(
                descriptionLastFM.description,
                descriptionLastFM.infoURL,
                Source.LASTFM,
                ""
            )


        } catch (e: Exception) {
            cardDescriptionLastFM = null
        }

        return cardDescriptionLastFM ?: EmptyCard
    }

}