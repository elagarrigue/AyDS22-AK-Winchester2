package ayds.winchester.songinfo.moredetails.home.controller

import ayds.winchester.songinfo.moredetails.home.model.OtherInfoModelInjector
import ayds.winchester.songinfo.moredetails.home.view.OtherInfoWindow

object OtherInfoControllerInjector {

    fun onViewStarted(otherInfoView: OtherInfoWindow) {
        OtherInfoControllerImpl(OtherInfoModelInjector.getOtherInfoModel()).apply {
            setView(otherInfoView)
        }
    }
}