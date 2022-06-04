package ayds.winchester.songinfo.moredetails.view

sealed class OtherInfoWindowEvent {
    object FullArticleWikipedia : OtherInfoWindowEvent()
    object FullArticleLastFM : OtherInfoWindowEvent()
    object FullArticleNYTimes : OtherInfoWindowEvent()
    object SearchDescription : OtherInfoWindowEvent()
}
