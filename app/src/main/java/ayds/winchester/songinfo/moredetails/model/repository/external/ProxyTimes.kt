package ayds.winchester.songinfo.moredetails.model.repository.external

import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.entities.CardArtistDescription
import ayds.ak1.newyorktimes.article.external.NYArticle
import ayds.ak1.newyorktimes.article.external.NYInjector.nyInfoService
import ayds.winchester.songinfo.moredetails.model.entities.EmptyCard
import ayds.winchester.songinfo.moredetails.model.entities.Source

interface ProxyTimes {
    fun getInfo (name : String) : Card
}


internal class ProxyTimesImpl : ProxyTimes {

    override fun getInfo (name : String) : Card {
        var cardDescriptionNewYorkTimes: CardArtistDescription?
        val descriptionNewYorkTimes: NYArticle

        try {

            descriptionNewYorkTimes = nyInfoService.getArtistInfo(name)!!

            cardDescriptionNewYorkTimes = CardArtistDescription(
                descriptionNewYorkTimes.description,
                descriptionNewYorkTimes.infoURL,
                Source.NEWYORKTIMES,
                descriptionNewYorkTimes.logoURL
            )

        } catch (e: Exception) {
            cardDescriptionNewYorkTimes = null
        }

        return cardDescriptionNewYorkTimes ?: EmptyCard
    }

}