package ayds.winchester.songinfo.moredetails.view

sealed class OtherInfoWindowEvent {
    object FullArticle : OtherInfoWindowEvent()
    object SearchDescription : OtherInfoWindowEvent()
}