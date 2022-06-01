package ayds.winchester.songinfo.moredetails.model.repository.external

import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.entities.EmptyCard

interface BrokerService{
    fun getInfo (name : String) :  List<Card>
}
internal class BrokerServiceImpl : BrokerService{
    
    private val proxyWikipedia = ProxyWikipediaImpl()
    private val proxyLastFM = ProxyLastFMImpl()
    
    override fun getInfo (name : String) : List<Card>{
        var emptyCardCount = 0
        
        var cardList : MutableList<Card> = mutableListOf()
        
        cardList.add(proxyWikipedia.getInfo(name))
        
        if(cardList.last()==EmptyCard)
            emptyCardCount++
        
        cardList.add(proxyLastFM.getInfo(name))

        if(cardList.last()==EmptyCard)
            emptyCardCount++
        
        if(emptyCardCount==cardList.size)
            cardList= mutableListOf()
        
        return cardList
    }
}
