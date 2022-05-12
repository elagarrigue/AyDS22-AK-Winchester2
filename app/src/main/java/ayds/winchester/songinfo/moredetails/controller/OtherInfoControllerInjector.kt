package ayds.winchester.songinfo.moredetails.controller

import ayds.winchester.songinfo.moredetails.model.OtherInfoModelInjector
import ayds.winchester.songinfo.moredetails.view.OtherInfoWindow

object OtherInfoControllerInjector {

    fun initOtherInfoController(otherInfoView: OtherInfoWindow) {
        OtherInfoControllerImpl(OtherInfoModelInjector.getOtherInfoModel()).apply {
            setOtherInfoWindow(otherInfoView)
        }
    }

}