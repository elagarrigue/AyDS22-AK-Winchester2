package ayds.winchester.songinfo.moredetails.model

import ayds.winchester.songinfo.moredetails.model.repository.DescriptionRepository
import ayds.winchester.songinfo.moredetails.model.repository.DescriptionRepositoryImpl
import ayds.winchester.songinfo.moredetails.model.repository.external.BrokerInjector
import ayds.winchester.songinfo.moredetails.model.repository.local.LocalRepositoryImpl
import ayds.winchester.songinfo.moredetails.view.OtherInfoWindow

object OtherInfoModelInjector {

    private lateinit var otherInfoModel: OtherInfoModelImpl

    fun getOtherInfoModel(): OtherInfoModel = otherInfoModel

    fun initOtherInfoModel (otherInfoWindow: OtherInfoWindow){
        
        val localRepository = LocalRepositoryImpl(otherInfoWindow)
        val repository: DescriptionRepository =
            DescriptionRepositoryImpl(localRepository,BrokerInjector.broker)

        otherInfoModel = OtherInfoModelImpl(repository)

    }
}