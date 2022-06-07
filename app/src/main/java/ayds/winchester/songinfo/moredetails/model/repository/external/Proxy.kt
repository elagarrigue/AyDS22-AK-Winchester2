package ayds.winchester.songinfo.moredetails.model.repository.external

import ayds.winchester.songinfo.moredetails.model.entities.Card

interface Proxy {
    fun getInfo(name: String): Card
}