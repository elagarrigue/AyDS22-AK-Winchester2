package ayds.winchester.songinfo.moredetails.model.repository.external

import ayds.lisboa2.lastFM.LastFMInjector.lastFMService
import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.entities.CardArtistDescription
import ayds.winchester2.wikipedia.WikipediaInjector.wikipediaService

interface BrokerService{
    fun getInfo (name : String) :  List<Card>
}
internal class BrokerServiceImpl : BrokerService{
    override fun getInfo (name : String) : List<Card>{
        var cardDescriptionWikipedia : CardArtistDescription? = null
        var cardDescriptionLastFM : CardArtistDescription? = null
        var cardList : MutableList<Card> = mutableListOf()
        try {
            val descriptionLastFM = lastFMService.getArtist(name)
            val descriptionWikipedia = wikipediaService.getArtistDescription(name)
            cardDescriptionWikipedia = CardArtistDescription(descriptionWikipedia.description,"https://en.wikipedia.org/?curid="+descriptionWikipedia.id,"Wikipedia","")
            cardList.add(cardDescriptionWikipedia)
            if (descriptionLastFM != null) {
                    cardDescriptionLastFM = CardArtistDescription(
                    descriptionLastFM.description,
                    descriptionLastFM.infoURL,
                    "LastFM",
                    ""
                )
                cardList.add(cardDescriptionLastFM)
            };
        } catch (e: Exception) {

        }
        return cardList
    }
}