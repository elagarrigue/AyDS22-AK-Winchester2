package ayds.winchester.songinfo.moredetails.home.model

import ayds.winchester.songinfo.moredetails.home.model.repository.DescriptionRepository
import ayds.winchester.songinfo.moredetails.home.model.repository.DescriptionRepositoryImpl
import ayds.winchester.songinfo.moredetails.home.model.repository.external.WikipediaInjector
import ayds.winchester.songinfo.moredetails.home.model.repository.local.LocalRepositoryImpl
import ayds.winchester.songinfo.moredetails.home.view.OtherInfoWindow

object OtherInfoModelInjector {

    private lateinit var otherInfoModel: OtherInfoModelImpl

    fun getOtherInfoModel(): OtherInfoModelImpl = otherInfoModel

    fun initOtherInfoModel (otherInfoWindow: OtherInfoWindow){

        val localRepository = LocalRepositoryImpl(otherInfoWindow)
        val externalRepository = WikipediaInjector.wikipediaService
        val repository: DescriptionRepository =
            DescriptionRepositoryImpl(localRepository, externalRepository)

        otherInfoModel = OtherInfoModelImpl(repository)

    }
}