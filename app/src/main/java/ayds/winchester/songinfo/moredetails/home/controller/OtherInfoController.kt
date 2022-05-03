package ayds.winchester.songinfo.moredetails.home.controller

import ayds.observer.Observer
import ayds.winchester.songinfo.moredetails.home.model.OtherInfoModel
import ayds.winchester.songinfo.moredetails.home.view.MoreDetailsUiEvent
import ayds.winchester.songinfo.moredetails.home.view.OtherInfoWindow

interface OtherInfoController {

    fun setOtherInfoWindow()
}

class OtherInfoControllerImpl {
   private lateinit var otherInfoModel : OtherInfoModel
   private lateinit var otherInfoWindow: OtherInfoWindow

   fun init (){

   }

   fun searchArtistDescription(name : String){
        otherInfoModel.setNameArtist(name)
    }

    fun setView(view : OtherInfoWindow){
        otherInfoWindow = view
        otherInfoModel = OtherInfoModel(otherInfoWindow)
        otherInfoWindow.uiEventObservable.subscribe(observer)
    }



    private val observer: Observer<MoreDetailsUiEvent> =
        Observer { artistName -> searchArtistDescription(artistName.name)    }

}