package ayds.winchester.songinfo.moredetails.home.controller

import ayds.observer.Observer
import ayds.winchester.songinfo.moredetails.home.model.OtherInfoModel
import ayds.winchester.songinfo.moredetails.home.view.MoreDetailsUiEvent
import ayds.winchester.songinfo.moredetails.home.view.OtherInfoWindow

interface OtherInfoController {

    fun setOtherInfoWindow(view : OtherInfoWindow)
}

class OtherInfoControllerImpl(private var otherInfoModel: OtherInfoModel) : OtherInfoController {
   private lateinit var otherInfoWindow: OtherInfoWindow

   private fun searchArtistDescription(name : String){
        otherInfoModel.searchArtistName(name)
   }

    override fun setOtherInfoWindow(view : OtherInfoWindow){
        otherInfoWindow = view
        otherInfoWindow.uiEventObservable.subscribe(observer)
    }

    private val observer: Observer<MoreDetailsUiEvent> =
        Observer { artistName -> searchArtistDescription(artistName.name)    }

}