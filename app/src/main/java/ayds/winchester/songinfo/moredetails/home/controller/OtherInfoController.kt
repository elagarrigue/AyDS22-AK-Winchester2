package ayds.winchester.songinfo.moredetails.home.controller

import ayds.winchester.songinfo.moredetails.home.model.OtherInfoModel
import ayds.winchester.songinfo.moredetails.home.view.OtherInfoWindow

interface OtherInfoController {

    fun setOtherInfoWindow()
}

class OtherInfoControllerImpl {
   private lateinit var otherInfoModel : OtherInfoModel
   private lateinit var otherInfoWindow: OtherInfoWindow

    fun searchArtistDescription(name : String){
        otherInfoModel.setNameArtist(name)
    }

    fun setVista(view : OtherInfoWindow){
        otherInfoWindow = view
        otherInfoModel = OtherInfoModel(otherInfoWindow)
    }



}