package ayds.winchester.songinfo.otherinfo.model

import ayds.winchester.songinfo.moredetails.model.OtherInfoModel
import ayds.winchester.songinfo.moredetails.model.OtherInfoModelImpl
import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.repository.CardRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class OtherInfoModelTest {
    private val repository: CardRepository = mockk()

    private val otherInfoModel: OtherInfoModel by lazy {
        OtherInfoModelImpl(repository)
    }

     @Test
     fun `on search cards it should notify the result`() {
         val cards: List<Card> = mockk()
         every { repository.getCards("artist") } returns cards
         val descriptionTester: (List<Card>) -> Unit = mockk(relaxed = true)
         otherInfoModel.uiEventObservable.subscribe() {
             descriptionTester(it)
         }

         otherInfoModel.searchArtistName("artist")

         verify { descriptionTester(cards) }
     }
}