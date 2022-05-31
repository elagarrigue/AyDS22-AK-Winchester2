package ayds.winchester.songinfo.moredetails.model.repository.external

import ayds.lisboa2.lastFM.LastFMArtist
import ayds.lisboa2.lastFM.LastFMInjector.lastFMService
import ayds.lisboa2.lastFM.LastFMService
import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.entities.CardArtistDescription
import ayds.winchester.songinfo.moredetails.model.entities.Source
import ayds.winchester2.wikipedia.WikipediaArticle
import ayds.winchester2.wikipedia.WikipediaInjector.wikipediaService

interface BrokerService{
    fun getInfo (name : String) :  List<Card>
}
internal class BrokerServiceImpl : BrokerService{
    override fun getInfo (name : String) : List<Card>{
        var cardDescriptionWikipedia : CardArtistDescription? = null
        var cardDescriptionLastFM : CardArtistDescription? = null
       // var cardDescriptionNewYorkTimes : CardArtistDescription? = null
        var cardList : MutableList<Card> = mutableListOf()
        val descriptionLastFM : LastFMArtist
        val descriptionWikipedia : WikipediaArticle
       // val descriptionNewYorkTimes : WikipediaArticle
        try {
            descriptionLastFM = lastFMService.getArtist(name)!!
            descriptionWikipedia = wikipediaService.getArtistDescription(name)
          //  descriptionNewYorkTimes = wikipediaService.getArtistDescription(name)
            cardDescriptionWikipedia = CardArtistDescription(descriptionWikipedia.description,"https://en.wikipedia.org/?curid="+descriptionWikipedia.id,
                Source.WIKIPEDIA,"")
            cardList.add(cardDescriptionWikipedia)
            if (descriptionLastFM != null) {
                    cardDescriptionLastFM = CardArtistDescription(
                    descriptionLastFM.description,
                    descriptionLastFM.infoURL,
                    Source.LASTFM,
                    ""
                )
                cardList.add(cardDescriptionLastFM)
            };
        } catch (e: Exception) {

        }
        return cardList
    }
}