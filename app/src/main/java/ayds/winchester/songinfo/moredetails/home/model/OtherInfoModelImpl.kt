package ayds.winchester.songinfo.moredetails.home.model

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.moredetails.home.model.entities.Description
import ayds.winchester.songinfo.moredetails.home.model.repository.DescriptionRepository

interface OtherInfoModel{
    val uiEventObservable: Observable<Description>
    fun searchArtistName(name: String)
}

class OtherInfoModelImpl(private var repository: DescriptionRepository) : OtherInfoModel{
    private lateinit var artistName: String
    private val onActionSubject = Subject<Description>()
    override val uiEventObservable: Observable<Description> = onActionSubject

    private fun getArtistInfo() {
        Thread {
            val description = repository.getDescription(artistName)
            onActionSubject.notify(description)
        }.start()
    }

    override fun searchArtistName(name: String) {
        artistName = name
        getArtistInfo()
    }
}