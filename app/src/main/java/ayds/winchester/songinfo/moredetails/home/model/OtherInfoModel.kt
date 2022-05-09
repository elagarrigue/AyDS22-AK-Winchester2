package ayds.winchester.songinfo.moredetails.home.model

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.moredetails.home.model.entities.Description
import ayds.winchester.songinfo.moredetails.home.model.repository.DescriptionRepository
import ayds.winchester.songinfo.moredetails.home.view.OtherInfoWindow

class OtherInfoModel(repository: DescriptionRepository) {
    private lateinit var artistName: String
    private val onActionSubject = Subject<Description>()
    val uiEventObservable: Observable<Description> = onActionSubject
    private var repository = repository

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