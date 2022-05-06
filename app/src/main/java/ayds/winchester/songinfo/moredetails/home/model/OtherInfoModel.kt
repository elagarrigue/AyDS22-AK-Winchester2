package ayds.winchester.songinfo.moredetails.home.model

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.moredetails.home.model.entities.Description
import ayds.winchester.songinfo.moredetails.home.model.repository.DescriptionRepositoryImpl
import ayds.winchester.songinfo.moredetails.home.view.OtherInfoWindow


class OtherInfoModel(otherInfoWindow: OtherInfoWindow) {
    private lateinit var artistName: String
    private var otherInfoView: OtherInfoWindow = otherInfoWindow
    private val onActionSubject = Subject<Description>()
    val uiEventObservable: Observable<Description> = onActionSubject
    private var repository = DescriptionRepositoryImpl(otherInfoView)

    private fun getArtistInfo() {
        Thread {
            val description = repository.getDescription(artistName)
            onActionSubject.notify(description)
        }.start()
    }

    fun setNameArtist(name: String) {
        artistName = name
        getArtistInfo()//----------------------Mover
    }
}