package ayds.winchester.songinfo.moredetails.home.controller

import ayds.winchester.songinfo.moredetails.home.model.OtherInfoModelInjector
import ayds.winchester.songinfo.moredetails.home.view.OtherInfoWindow

object OtherInfoControllerInjector {

    fun initOtherInfoController(otherInfoView: OtherInfoWindow) {
        OtherInfoControllerImpl(OtherInfoModelInjector.getOtherInfoModel()).apply {
            setOtherInfoWindow(otherInfoView)
        }
    }

}