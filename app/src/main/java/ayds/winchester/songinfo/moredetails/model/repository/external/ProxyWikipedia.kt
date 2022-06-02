package ayds.winchester.songinfo.moredetails.model.repository.external

import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.entities.EmptyCard
import ayds.winchester.songinfo.moredetails.model.entities.CardArtistDescription
import ayds.winchester.songinfo.moredetails.model.entities.Source
import ayds.winchester2.wikipedia.WikipediaArticle
import ayds.winchester2.wikipedia.WikipediaInjector

interface ProxyWikipedia{
    fun getInfo(name: String) : Card
}

internal class ProxyWikipediaImpl : ProxyWikipedia {

    override fun getInfo (name : String) : Card {
        var cardDescriptionWikipedia: CardArtistDescription? = null
        val descriptionWikipedia: WikipediaArticle

        try {

            descriptionWikipedia = WikipediaInjector.wikipediaService.getArtistDescription(name)

            cardDescriptionWikipedia = CardArtistDescription(
                descriptionWikipedia.description,
                descriptionWikipedia.source,
                Source.WIKIPEDIA,
                descriptionWikipedia.sourceLogo
            )

        } catch (e: Exception) {
            cardDescriptionWikipedia = null
        }

        return cardDescriptionWikipedia ?: EmptyCard
    }

}