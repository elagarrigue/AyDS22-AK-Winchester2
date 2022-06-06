package ayds.winchester.songinfo.moredetails.model.repository.external

object BrokerInjector {

    private val proxyWikipedia = ProxyWikipediaImpl()
    private val proxyLastFM = ProxyLastFMImpl()
    private val proxyTimes = ProxyTimesImpl()

    internal val broker : BrokerService = BrokerServiceImpl(proxyWikipedia, proxyLastFM, proxyTimes)
}