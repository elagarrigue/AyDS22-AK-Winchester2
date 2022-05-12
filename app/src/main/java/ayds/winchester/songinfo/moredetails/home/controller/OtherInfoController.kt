package ayds.winchester.songinfo.moredetails.home.controller

import ayds.observer.Observer
import ayds.winchester.songinfo.moredetails.home.model.OtherInfoModel
import ayds.winchester.songinfo.moredetails.home.view.MoreDetailsUiEvent
import ayds.winchester.songinfo.moredetails.home.view.OtherInfoWindow
import ayds.winchester.songinfo.moredetails.home.view.OtherInfoWindowEvent

interface OtherInfoController {

    fun setOtherInfoWindow(view : OtherInfoWindow)
}

internal class OtherInfoControllerImpl(private var otherInfoModel: OtherInfoModel) : OtherInfoController {
   private lateinit var otherInfoWindow: OtherInfoWindow

   private fun searchArtistDescription(name : String){
        otherInfoModel.searchArtistName(name)
   }

    override fun setOtherInfoWindow(view : OtherInfoWindow){
        otherInfoWindow = view
        otherInfoWindow.uiEventObservable.subscribe(observer)
        otherInfoWindow.uiEventObservableFullArticle.subscribe(observerFullArticle)
    }

    private fun openArticleUrl() {
        otherInfoWindow.openExternalLink("holaa")
    }

    private val observer: Observer<MoreDetailsUiEvent> =
        Observer { artistName -> searchArtistDescription(artistName.name)    }

    private val observerFullArticle: Observer<OtherInfoWindowEvent> =
        Observer { value ->
        when (value) {
            OtherInfoWindowEvent.fullArticle -> openArticleUrl()
        }
    }

}