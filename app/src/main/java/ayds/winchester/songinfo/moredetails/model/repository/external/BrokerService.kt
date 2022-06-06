package ayds.winchester.songinfo.moredetails.model.repository.external

import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.entities.EmptyCard

interface BrokerService{
    fun getInfo (name : String) :  List<Card>
}
internal class BrokerServiceImpl(
    private val proxyWikipedia : ProxyWikipedia,
    private val proxyLastFM : ProxyLastFM,
    private val proxyNewYorkTimes : ProxyTimes
) : BrokerService {

    override fun getInfo (name : String) : List<Card>{
val cardList: MutableList<Card> = mutableListOf()

        cardList.add(proxyLastFM.getInfo(name))
        cardList.add(proxyWikipedia.getInfo(name))
        cardList.add(proxyNewYorkTimes.getInfo(name))

        return if (cardList.all { it is EmptyCard })
            listOf()
        else
            cardList
    }
}
