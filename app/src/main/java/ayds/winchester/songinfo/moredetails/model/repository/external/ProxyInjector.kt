package ayds.winchester.songinfo.moredetails.model.repository.external

import ayds.ak1.newyorktimes.article.external.NYInjector.nyInfoService
import ayds.lisboa2.lastFM.LastFMInjector.lastFMService
import ayds.winchester2.wikipedia.WikipediaInjector.wikipediaService

object ProxyInjector {

    val proxyLastFM : Proxy = ProxyLastFMImpl(lastFMService)
    val proxyTimes : Proxy = ProxyTimesImpl(nyInfoService)
    val proxyWikipedia : Proxy = ProxyWikipediaImpl(wikipediaService)

}