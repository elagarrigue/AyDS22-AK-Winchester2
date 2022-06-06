package ayds.winchester.songinfo.moredetails.view

import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.entities.Source


data class OtherInfoUIState(
    var artistName : String = "",
    var actionsEnabled: MutableList<Boolean> = mutableListOf(false, false, false),
    val cardList: MutableList<Card> = mutableListOf()
) {

    companion object {
        const val URL_IMAGE_WIKIPEDIA = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"
        const val URL_IMAGE_LASTFM = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
        const val URL_IMAGE_TIMES = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVioI832nuYIXqzySD8cOXRZEcdlAj3KfxA62UEC4FhrHVe0f7oZXp3_mSFG7nIcUKhg&usqp=CAU"
        const val URL_IMAGE_NOT_FOUND = "https://alfabetajuega.com/hero/2018/10/167687.alfabetajuega-problemas-tecnicos.jpg"

    }

    fun getURL (source: Source) : String{
        return cardList.filter { it.source == source }.last().infoUrl
    }
}