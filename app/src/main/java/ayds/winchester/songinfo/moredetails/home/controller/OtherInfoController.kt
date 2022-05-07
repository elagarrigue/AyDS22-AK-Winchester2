package ayds.winchester.songinfo.moredetails.home.controller

import ayds.observer.Observer
import ayds.winchester.songinfo.moredetails.home.model.OtherInfoModelInjector
import ayds.winchester.songinfo.moredetails.home.model.OtherInfoModel
import ayds.winchester.songinfo.moredetails.home.view.MoreDetailsUiEvent
import ayds.winchester.songinfo.moredetails.home.view.OtherInfoWindow

interface OtherInfoController {

    fun setOtherInfoWindow()
}

class OtherInfoControllerImpl(otherInfoModel: OtherInfoModel ) {
   private var otherInfoModel = otherInfoModel
   private lateinit var otherInfoWindow: OtherInfoWindow

   fun init (){

   }

   private fun searchArtistDescription(name : String){
        otherInfoModel.setNameArtist(name)
   }

    fun setView(view : OtherInfoWindow){
        otherInfoWindow = view
        otherInfoWindow.uiEventObservable.subscribe(observer)
    }

    private val observer: Observer<MoreDetailsUiEvent> =
        Observer { artistName -> searchArtistDescription(artistName.name)    }

    fun getModel():OtherInfoModel{
        return otherInfoModel
    }

}