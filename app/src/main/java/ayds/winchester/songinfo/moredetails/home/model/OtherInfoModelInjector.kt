package ayds.winchester.songinfo.moredetails.home.model

import ayds.winchester.songinfo.moredetails.home.model.repository.DescriptionRepository
import ayds.winchester.songinfo.moredetails.home.model.repository.DescriptionRepositoryImpl
import ayds.winchester.songinfo.moredetails.home.model.repository.external.ExternalRepository
import ayds.winchester.songinfo.moredetails.home.model.repository.external.WikipediaInjector
import ayds.winchester.songinfo.moredetails.home.model.repository.local.LocalRepository
import ayds.winchester.songinfo.moredetails.home.view.OtherInfoWindow

object OtherInfoModelInjector {

    private lateinit var otherInfoModel: OtherInfoModel

    fun getOtherInfoModel(): OtherInfoModel = otherInfoModel

    fun initOtherInfoModel (otherInfoWindow: OtherInfoWindow){

        val localRepository = LocalRepository(otherInfoWindow)
        val externalRepository = WikipediaInjector.wikipediaService
        val repository: DescriptionRepository =
            DescriptionRepositoryImpl(localRepository, externalRepository)

        otherInfoModel = OtherInfoModel(repository)


    }
}