package ayds.winchester.songinfo.moredetails.model.repository.external

object BrokerInjector {

    private val proxyWikipedia = ProxyInjector.proxyWikipedia
    private val proxyLastFM = ProxyInjector.proxyLastFM
    private val proxyTimes = ProxyInjector.proxyTimes

    internal val broker : BrokerService = BrokerServiceImpl(proxyWikipedia, proxyLastFM, proxyTimes)
}