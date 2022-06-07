package ayds.winchester.songinfo.moredetails.view

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import ayds.winchester.songinfo.R
import com.squareup.picasso.Picasso
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.view.isVisible
import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.moredetails.model.OtherInfoModelInjector
import ayds.winchester.songinfo.moredetails.model.OtherInfoModel
import ayds.winchester.songinfo.moredetails.model.entities.*
import ayds.winchester.songinfo.moredetails.view.OtherInfoUIState.Companion.NO_RESULTS
import ayds.winchester.songinfo.moredetails.view.OtherInfoUIState.Companion.SOURCE
import ayds.winchester.songinfo.moredetails.view.OtherInfoUIState.Companion.URL_IMAGE_NOT_FOUND
import ayds.winchester.songinfo.utils.UtilsInjector
import ayds.winchester.songinfo.utils.navigation.NavigationUtils

interface OtherInfoWindow {
    var uiState: OtherInfoUIState
    val uiEventObservableFullArticle: Observable<OtherInfoWindowEvent>
    fun openExternalLink(url: String)
}

internal class OtherInfoWindowImpl : AppCompatActivity(), OtherInfoWindow {

    private var descriptionsTexts: MutableList<TextView> = mutableListOf()
    private var descriptionsImages: MutableList<ImageView> = mutableListOf()
    private var descriptionsSources: MutableList<TextView> = mutableListOf()
    private var descriptionsButtons: MutableList<Button> = mutableListOf()

    private val onActionSubject = Subject<OtherInfoWindowEvent>()
    private lateinit var otherInfoModel: OtherInfoModel
    private val navigationUtils: NavigationUtils = UtilsInjector.navigationUtils
    private val cardDescriptionHelper: CardDescriptionHelper = CardDescriptionHelperImpl()
    override var uiState = OtherInfoUIState()
    override val uiEventObservableFullArticle = onActionSubject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        updateStateArtistName()
        initModule()
        initViewProperties()
        notifySearchDescriptionAction()
        initObservers()
    }

    private fun initModule() {
        OtherInfoWindowInjector.init(this)
        otherInfoModel = OtherInfoModelInjector.getOtherInfoModel()
    }

    private fun initObservers() {
        otherInfoModel.uiEventObservable
            .subscribe { value -> updateCards(value) }
    }

    private fun updateUiState(cards: List<Card>) {
        for (i in cards.indices) {
            when (cards[i]) {
                is CardDescription -> updateCards(cards[i])
                EmptyCard -> updateCardNoResult(cards[i])
            }
        }
    }

    private fun updateCards(cards: List<Card>) {

        updateUiState(cards)
        showUI(cards)
    }

    private fun updateCards(card: Card) {
        val cardUI = CardUI(
            card.description,
            card.infoUrl,
            card.source,
            card.sourceLogoUrl,
            card.isLocallyStored
        )
        cardUI.isEnabled = true
        uiState.cardList.add(cardUI)
    }

    private fun updateCardNoResult(card: Card) {
        val cardUI = CardUI(
            card.description,
            card.infoUrl,
            card.source,
            card.sourceLogoUrl,
            card.isLocallyStored
        )
        cardUI.isEnabled = false
        uiState.cardList.add(cardUI)
    }

    private fun updateStateArtistName() {
        uiState = uiState.copy(
            artistName = intent.getStringExtra(ARTIST_NAME) ?: "",
        )
    }

    private fun notifySearchDescriptionAction() {
        onActionSubject.notify(OtherInfoWindowEvent.SearchCard)
    }

    private fun initViewProperties() {

        descriptionsTexts.add(findViewById(R.id.textPaneArtistDescription1))
        descriptionsTexts.add(findViewById(R.id.textPaneArtistDescription2))
        descriptionsTexts.add(findViewById(R.id.textPaneArtistDescription3))

        descriptionsSources.add(findViewById(R.id.sourceText1))
        descriptionsSources.add(findViewById(R.id.sourceText2))
        descriptionsSources.add(findViewById(R.id.sourceText3))

        descriptionsImages.add(findViewById<View>(R.id.imageView1) as ImageView)
        descriptionsImages.add(findViewById<View>(R.id.imageView2) as ImageView)
        descriptionsImages.add(findViewById<View>(R.id.imageView3) as ImageView)

        descriptionsButtons.add(findViewById(R.id.openUrlButton1))
        descriptionsButtons.add(findViewById(R.id.openUrlButton2))
        descriptionsButtons.add(findViewById(R.id.openUrlButton3))
    }

    private fun notifyFullArticleAction(source: Source) {
        onActionSubject.notify(OtherInfoWindowEvent.FullPage(source))
    }

    private fun setViewFullArticleButtonOnClick(cards: List<Card>) {
        for (i in cards.indices) {
            descriptionsButtons[i].setOnClickListener {
                notifyFullArticleAction(cards[i].source)
            }
        }
    }

    override fun openExternalLink(url: String) {
        navigationUtils.openExternalUrl(this, url)
    }

    private fun showUI(cards: List<Card>) {
        runOnUiThread {
            if (cards.isNotEmpty()) {
                showImage(cards)
                showDescription(cards)
                enableActions(cards)
                setViewFullArticleButtonOnClick(cards)
            } else {
                showUINoResults()
                disabledButtons()
            }
        }
    }

    private fun showUINoResults() {
        descriptionsTexts[0].text = NO_RESULTS
        Picasso.get().load(URL_IMAGE_NOT_FOUND).into(descriptionsImages[0])
    }

    private fun disabledButtons() {
        for (i in descriptionsButtons.indices) {
            descriptionsButtons[i].isEnabled = false
            descriptionsButtons[i].isVisible = false
        }
    }

    private fun enableActions(cards: List<Card>) {
        val count = enableButtons(cards)

        hiddenButtons(count)
    }

    private fun enableButtons(cards: List<Card>): Int {
        var count = 0
        for (i in cards.indices) {
            descriptionsButtons[i].isEnabled = uiState.cardList[i].isEnabled
            count++
        }
        return count
    }

    private fun hiddenButtons(count: Int) {
        for (i in count until descriptionsButtons.size) {
            descriptionsButtons[i].isVisible = false
        }
    }

    private fun showImage(cards: List<Card>) {
        for (i in cards.indices) {
            Picasso.get().load(uiState.getImageURL(cards[i].source)).into(descriptionsImages[i])
        }
    }

    private fun showDescription(cards: List<Card>) {
        for (i in cards.indices) {
            descriptionsTexts[i].text =
                Html.fromHtml(cardDescriptionHelper.getTextCard(cards[i], uiState.artistName))
            descriptionsSources[i].text = SOURCE + cards[i].source
        }
    }

    companion object {
        const val ARTIST_NAME = "artistName"
    }
}