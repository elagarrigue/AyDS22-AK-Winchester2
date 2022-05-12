package ayds.winchester.songinfo.moredetails.view

import ayds.winchester.songinfo.moredetails.controller.OtherInfoControllerInjector
import ayds.winchester.songinfo.moredetails.model.OtherInfoModelInjector

object OtherInfoWindowInjector {

    fun init(otherInfoWindow:OtherInfoWindow){
        OtherInfoModelInjector.initOtherInfoModel(otherInfoWindow)
        OtherInfoControllerInjector.initOtherInfoController(otherInfoWindow)
    }
}