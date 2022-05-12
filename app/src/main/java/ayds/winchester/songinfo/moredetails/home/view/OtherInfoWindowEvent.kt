package ayds.winchester.songinfo.moredetails.home.view

sealed class OtherInfoWindowEvent {
    object FullArticle : OtherInfoWindowEvent()
    object SearchDescription : OtherInfoWindowEvent()
}