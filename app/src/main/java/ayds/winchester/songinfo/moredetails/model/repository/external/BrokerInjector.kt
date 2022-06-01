package ayds.winchester.songinfo.moredetails.model.repository.external

object BrokerInjector {

    private val proxyWikipedia = ProxyWikipediaImpl()
    private val proxyLastFM = ProxyLastFMImpl()

    internal val broker = BrokerServiceImpl(proxyWikipedia,proxyLastFM)
}