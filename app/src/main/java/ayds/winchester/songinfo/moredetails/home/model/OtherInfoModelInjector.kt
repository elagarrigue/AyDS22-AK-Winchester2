package ayds.winchester.songinfo.moredetails.home.model

import ayds.winchester.songinfo.moredetails.home.view.OtherInfoWindow

object OtherInfoModelInjector {

    private lateinit var otherInfoModel: OtherInfoModel

    fun getOtherInfoModel(): OtherInfoModel = otherInfoModel

    fun initOtherInfoModel (otherInfoWindow: OtherInfoWindow){
        otherInfoModel = OtherInfoModel(otherInfoWindow)
        println("==================")
    }
}