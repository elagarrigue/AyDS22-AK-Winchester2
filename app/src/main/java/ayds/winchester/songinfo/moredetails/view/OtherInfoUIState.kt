package ayds.winchester.songinfo.moredetails.view

import ayds.winchester.songinfo.moredetails.model.entities.CardUI
import ayds.winchester.songinfo.moredetails.model.entities.Source

data class OtherInfoUIState(
    var artistName: String = "",
    val cardList: MutableList<CardUI> = mutableListOf()
) {
    companion object {
        const val SOURCE = "source: "
        const val NO_RESULTS = "No results"

    }

    fun getInfoURL(source: Source): String {
        return cardList.filter { it.source == source }.last().infoUrl
    }

}