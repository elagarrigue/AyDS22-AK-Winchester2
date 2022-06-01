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
import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.moredetails.model.OtherInfoModelInjector
import ayds.winchester.songinfo.moredetails.model.OtherInfoModel
import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.entities.CardArtistDescription
import ayds.winchester.songinfo.moredetails.model.entities.EmptyCard
import ayds.winchester.songinfo.moredetails.model.entities.Source
import ayds.winchester.songinfo.moredetails.view.OtherInfoUIState.Companion.URL_IMAGE
import ayds.winchester.songinfo.moredetails.view.OtherInfoUIState.Companion.URL_IMAGE_LASTFM
import ayds.winchester.songinfo.moredetails.view.OtherInfoUIState.Companion.URL_IMAGE_TIMES
import ayds.winchester.songinfo.moredetails.view.OtherInfoUIState.Companion.URL_IMAGE_WIKIPEDIA
import ayds.winchester.songinfo.utils.UtilsInjector
import ayds.winchester.songinfo.utils.navigation.NavigationUtils

interface OtherInfoWindow {
    var uiState: OtherInfoUIState
    val uiEventObservableFullArticle : Observable<OtherInfoWindowEvent>
    fun openExternalLink(url: String)
}

private const val SOURCE="source: "

internal class OtherInfoWindowImpl : AppCompatActivity(),OtherInfoWindow {

    private var descriptionsTexts : MutableList<TextView> = mutableListOf()
    private var descriptionsImages : MutableList<ImageView> = mutableListOf()
    private var descriptionsSources : MutableList<TextView> = mutableListOf()
    private var descriptionsButtons : MutableList<Button> = mutableListOf()

    private lateinit var pageId: String

    private val onActionSubject = Subject<OtherInfoWindowEvent>()
    private lateinit var otherInfoModel : OtherInfoModel
    private val navigationUtils: NavigationUtils = UtilsInjector.navigationUtils
    private val artistDescriptionHelper : ArtistDescriptionHelper = ArtistDescriptionHelperImpl()
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

    private fun initModule(){
        OtherInfoWindowInjector.init(this)
        otherInfoModel = OtherInfoModelInjector.getOtherInfoModel()
    }

    private fun initObservers() {
        otherInfoModel.uiEventObservable
            .subscribe { value -> updateDescriptionInfo(value) }
    }

    private fun updateUiState(cards: List<Card>) {
        for(i in cards.indices) {
            when (cards[i]) {
                is CardArtistDescription -> updateArtistDescription(cards[i])
                EmptyCard -> updateArtistDescriptionNoResult()
            }
        }
    }

    private fun updateDescriptionInfo(cards: List<Card>) {
        updateUiState(cards)
        showUI(cards)
    }

    private fun updateArtistDescription(description: Card){
        uiState = uiState.copy(
            description = artistDescriptionHelper.getTextArtistDescription(description, uiState.artistName),
            WikipediaUrl = description.infoUrl,
            actionsEnabled = true
        )
    }

    private fun updateArtistDescriptionNoResult(){
        uiState = uiState.copy(
            actionsEnabled = false
        )
    }

    private fun updateStateArtistName() {
        uiState = uiState.copy(
            artistName = intent.getStringExtra(ARTIST_NAME) ?: "",
        )
    }

    private fun notifySearchDescriptionAction() {
       onActionSubject.notify(OtherInfoWindowEvent.SearchDescription)
    }

    private fun initViewProperties(){

        descriptionsTexts.add(findViewById(R.id.textPaneArtistDescription))
        descriptionsTexts.add(findViewById(R.id.textPaneArtistDescription2))
        //descriptionsTexts.add(findViewById(R.id.textPaneArtistDescription))

        descriptionsSources.add(findViewById(R.id.sourceText))
        descriptionsSources.add(findViewById(R.id.sourceText2))
        //descriptionsSources.add(findViewById(R.id.sourceText3))

        descriptionsImages.add(findViewById<View>(R.id.imageView) as ImageView)
        descriptionsImages.add(findViewById<View>(R.id.imageView2) as ImageView)
        //descriptionsImages.add(findViewById<View>(R.id.imageView3) as ImageView)

        descriptionsButtons.add(findViewById(R.id.openUrlButton))
        descriptionsButtons.add(findViewById(R.id.openUrlButton2))
        //descriptionsButtons.add(findViewById(R.id.openUrlButton3))

    }

    private fun notifyFullArticleAction() {
        onActionSubject.notify(OtherInfoWindowEvent.FullArticle)
    }

    private fun setViewFullArticleButtonOnClick(cards: List<Card>){

        for(i in cards.indices){
            descriptionsButtons[i].setOnClickListener {
                notifyFullArticleAction()
            }
        }


    }

    override fun openExternalLink(url: String) {
       navigationUtils.openExternalUrl(this, url)
    }

    private fun showUI(cards: List<Card>){
        runOnUiThread {
            showImage(cards)
            showDescription(cards)
            pageId = uiState.WikipediaUrl
            enableActions(cards)
            setViewFullArticleButtonOnClick(cards)
        }
    }

    private fun enableActions(cards: List<Card>) {
        for(i in cards.indices) {
            descriptionsButtons[i].isEnabled = uiState.actionsEnabled
        }
    }

    private fun showImage(cards: List<Card>){
        var imageUrl = URL_IMAGE
        for(i in cards.indices) {

            when(cards[i].source){
                Source.WIKIPEDIA->imageUrl = URL_IMAGE_WIKIPEDIA
                Source.LASTFM->imageUrl = URL_IMAGE_LASTFM
                Source.LASTFM->imageUrl = URL_IMAGE_TIMES
            }
            //
            Picasso.get().load(imageUrl).into(descriptionsImages[i])
        }
    }

    private fun showDescription(cards: List<Card>){

        for(i in cards.indices){
            descriptionsTexts[i].text=Html.fromHtml(artistDescriptionHelper.getTextArtistDescription(cards[i],uiState.artistName))
            descriptionsSources[i].text=SOURCE+cards[i].source
        }

    }

    companion object {
        const val ARTIST_NAME = "artistName"
    }
}