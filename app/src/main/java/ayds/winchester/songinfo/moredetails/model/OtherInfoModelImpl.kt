package ayds.winchester.songinfo.moredetails.model

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.repository.DescriptionRepository

interface OtherInfoModel{
    val uiEventObservable: Observable<Card>
    fun searchArtistName(name: String)
}

internal class OtherInfoModelImpl(private var repository: DescriptionRepository) : OtherInfoModel{
    private val onActionSubject = Subject<Card>()
    override val uiEventObservable: Observable<Card> = onActionSubject

    override fun searchArtistName(name : String) {
        Thread {
            val description = repository.getDescription(name)
            onActionSubject.notify(description.last())
        }.start()
    }
}