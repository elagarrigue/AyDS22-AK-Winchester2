package ayds.winchester.songinfo.moredetails.model.repository.external

import ayds.ak1.newyorktimes.article.external.NYInfoService
import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.entities.CardDescription
import ayds.ak1.newyorktimes.article.external.URL_NYTIMES_LOGO
import ayds.winchester.songinfo.moredetails.model.entities.EmptyCard
import ayds.winchester.songinfo.moredetails.model.entities.Source

internal class ProxyTimesImpl (private val nyInfoService: NYInfoService) : Proxy {

    override fun getInfo (name : String) : Card {
        var cardNewYorkTimes: CardDescription? = null

        try {
            nyInfoService.getArtistInfo(name)?.let {
                cardNewYorkTimes = CardDescription(
                    it.description,
                    it.infoURL,
                    Source.NEWYORKTIMES,
                    URL_NYTIMES_LOGO
                )
            }
        } catch (e: Exception) {}

        return cardNewYorkTimes ?: EmptyCard
    }
}