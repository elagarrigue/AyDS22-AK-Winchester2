package ayds.winchester.songinfo.moredetails.model.repository.external

import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.entities.EmptyCard
import ayds.winchester.songinfo.moredetails.model.entities.CardArtistDescription
import ayds.winchester.songinfo.moredetails.model.entities.Source
import ayds.winchester2.wikipedia.WikipediaArticle
import ayds.winchester2.wikipedia.WikipediaInjector

private const val URL = "https://en.wikipedia.org/?curid="

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
                URL + descriptionWikipedia.id,
                Source.WIKIPEDIA,
                ""
            )


        } catch (e: Exception) {
            cardDescriptionWikipedia = null
        }

        return cardDescriptionWikipedia ?: EmptyCard
    }

}