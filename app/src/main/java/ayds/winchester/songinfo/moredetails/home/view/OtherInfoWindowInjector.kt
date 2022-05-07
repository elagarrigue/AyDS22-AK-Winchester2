package ayds.winchester.songinfo.moredetails.home.view

import ayds.winchester.songinfo.moredetails.home.controller.OtherInfoControllerInjector
import ayds.winchester.songinfo.moredetails.home.model.OtherInfoModelInjector

object OtherInfoWindowInjector {

    fun init(otherInfoWindow:OtherInfoWindow){
        OtherInfoModelInjector.initOtherInfoModel(otherInfoWindow)
        OtherInfoControllerInjector.onViewStarted(otherInfoWindow)
    }
}