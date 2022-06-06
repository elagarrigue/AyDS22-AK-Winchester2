package ayds.winchester.songinfo.moredetails.view

import ayds.winchester.songinfo.moredetails.model.entities.Source

sealed class OtherInfoWindowEvent() {
    class FullPage (val source : Source) : OtherInfoWindowEvent()
    object SearchCard : OtherInfoWindowEvent()
}

