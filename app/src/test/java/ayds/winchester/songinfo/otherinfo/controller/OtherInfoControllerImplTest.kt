package ayds.winchester.songinfo.otherinfo.controller

import ayds.observer.Subject
import ayds.winchester.songinfo.moredetails.controller.OtherInfoControllerImpl
import ayds.winchester.songinfo.moredetails.model.OtherInfoModel
import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.entities.CardDescription
import ayds.winchester.songinfo.moredetails.model.entities.Source
import ayds.winchester.songinfo.moredetails.view.OtherInfoUIState
import ayds.winchester.songinfo.moredetails.view.OtherInfoWindow
import ayds.winchester.songinfo.moredetails.view.OtherInfoWindowEvent
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class OtherInfoControllerImplTest {

    private val otherInfoModel: OtherInfoModel = mockk(relaxUnitFun = true)

    private val onActionSubject = Subject<OtherInfoWindowEvent>()
    private val otherInfoWindow: OtherInfoWindow = mockk(relaxUnitFun = true) {
        every { uiEventObservableFullArticle } returns onActionSubject
    }

    private val otherInfoController by lazy {
        OtherInfoControllerImpl(otherInfoModel)
    }

    @Before
    fun setup() {
        otherInfoController.setOtherInfoWindow(otherInfoWindow)
    }

    @Test
    fun `on search event should search artist description`() {
        every { otherInfoWindow.uiState } returns OtherInfoUIState(artistName = "artist")

        onActionSubject.notify(OtherInfoWindowEvent.SearchCard)

        verify { otherInfoModel.searchArtistName("artist") }
    }

    @Test
    fun `on open full article event should navigate to the article`() {
        var cards: MutableList<Card> = mutableListOf()
        val card : Card = CardDescription("", "1", Source.WIKIPEDIA, "" )
        cards.add(card)

        every { otherInfoWindow.uiState } returns OtherInfoUIState(cardList = cards)

        onActionSubject.notify(OtherInfoWindowEvent.FullPage(Source.WIKIPEDIA))

        verify { otherInfoWindow.openExternalLink("1") }
    }
}