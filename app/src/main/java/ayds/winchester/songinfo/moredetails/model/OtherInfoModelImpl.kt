package ayds.winchester.songinfo.moredetails.model

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.repository.CardRepository

interface OtherInfoModel {
    val uiEventObservable: Observable<List<Card>>
    fun searchArtistName(name: String)
}

internal class OtherInfoModelImpl(private var repository: CardRepository) : OtherInfoModel {
    private val onActionSubject = Subject<List<Card>>()
    override val uiEventObservable: Observable<List<Card>> = onActionSubject

    override fun searchArtistName(name: String) {
        Thread {
            val cards = repository.getCards(name)
            onActionSubject.notify(cards)
        }.start()
    }
}