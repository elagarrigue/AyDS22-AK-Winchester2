package ayds.winchester.songinfo.moredetails.model.repository.external

import ayds.lisboa2.lastFM.LASTFM_LOGO
import ayds.lisboa2.lastFM.LastFMInjector.lastFMService
import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.entities.CardDescription
import ayds.winchester.songinfo.moredetails.model.entities.EmptyCard
import ayds.winchester.songinfo.moredetails.model.entities.Source

interface ProxyLastFM {
    fun getInfo (name : String) : Card
}

internal class ProxyLastFMImpl : ProxyLastFM {
    override fun getInfo (name : String) : Card {
        var cardDescriptionLastFM: CardDescription? = null

        try {
            lastFMService.getArtist(name)?.let {
                cardDescriptionLastFM = CardDescription(
                    it.description,
                    it.infoURL,
                    Source.LASTFM,
                    LASTFM_LOGO
                )
            }
        } catch (e: Exception) {}

        return cardDescriptionLastFM ?: EmptyCard
    }

}