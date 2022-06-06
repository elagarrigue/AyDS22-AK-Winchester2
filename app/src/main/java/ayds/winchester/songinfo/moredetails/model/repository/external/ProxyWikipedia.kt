package ayds.winchester.songinfo.moredetails.model.repository.external

import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.entities.EmptyCard
import ayds.winchester.songinfo.moredetails.model.entities.CardDescription
import ayds.winchester.songinfo.moredetails.model.entities.Source
import ayds.winchester2.wikipedia.WikipediaArticle
import ayds.winchester2.wikipedia.WikipediaInjector

internal class ProxyWikipediaImpl : Proxy {

    override fun getInfo (name : String) : Card {
        var cardWikipedia: CardDescription?
        val descriptionWikipedia: WikipediaArticle

        try {
            descriptionWikipedia = WikipediaInjector.wikipediaService.getArtistDescription(name)

            cardWikipedia = CardDescription(
                descriptionWikipedia.description,
                descriptionWikipedia.source,
                Source.WIKIPEDIA,
                descriptionWikipedia.sourceLogo
            )

        } catch (e: Exception) {
            cardWikipedia = null
        }

        return cardWikipedia ?: EmptyCard
    }

}