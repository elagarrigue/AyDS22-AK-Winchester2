package ayds.winchester.songinfo.moredetails.controller

import ayds.observer.Observer
import ayds.winchester.songinfo.moredetails.model.OtherInfoModel
import ayds.winchester.songinfo.moredetails.view.OtherInfoWindow
import ayds.winchester.songinfo.moredetails.view.OtherInfoWindowEvent

interface OtherInfoController {
    fun setOtherInfoWindow(view : OtherInfoWindow)
}

internal class OtherInfoControllerImpl(private var otherInfoModel: OtherInfoModel) : OtherInfoController {
   private lateinit var otherInfoWindow: OtherInfoWindow

   private fun searchArtistDescription(){
        otherInfoModel.searchArtistName(otherInfoWindow.uiState.artistName)
   }

    override fun setOtherInfoWindow(view : OtherInfoWindow){
        otherInfoWindow = view
        otherInfoWindow.uiEventObservableFullArticle.subscribe(observerFullArticle)
    }

    private fun openArticleUrl() {
        otherInfoWindow.openExternalLink(otherInfoWindow.uiState.WikipediaUrl)
    }

    private val observerFullArticle: Observer<OtherInfoWindowEvent> =
        Observer { value ->
        when (value) {
            OtherInfoWindowEvent.FullArticle -> openArticleUrl()
            OtherInfoWindowEvent.SearchDescription -> searchArtistDescription()
        }
    }
}