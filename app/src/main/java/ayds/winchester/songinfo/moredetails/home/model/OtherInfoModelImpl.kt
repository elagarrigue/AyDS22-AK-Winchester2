package ayds.winchester.songinfo.moredetails.home.model

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.moredetails.home.model.entities.Description
import ayds.winchester.songinfo.moredetails.home.model.repository.DescriptionRepository

interface OtherInfoModel{
    val uiEventObservable: Observable<Description>
    fun searchArtistName(name: String)
}

internal class OtherInfoModelImpl(private var repository: DescriptionRepository) : OtherInfoModel{
    private val onActionSubject = Subject<Description>()
    override val uiEventObservable: Observable<Description> = onActionSubject

    override fun searchArtistName(artistName : String) {
        Thread {
            val description = repository.getDescription(artistName)
            onActionSubject.notify(description)
        }.start()
    }

}