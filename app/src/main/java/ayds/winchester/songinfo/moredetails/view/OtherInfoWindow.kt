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
import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.entities.CardArtistDescription
import ayds.winchester.songinfo.moredetails.model.entities.EmptyCard
import ayds.winchester.songinfo.moredetails.model.entities.Source
import ayds.winchester.songinfo.moredetails.view.OtherInfoUIState.Companion.URL_IMAGE_LASTFM
import ayds.winchester.songinfo.moredetails.view.OtherInfoUIState.Companion.URL_IMAGE_NOT_FOUND
import ayds.winchester.songinfo.moredetails.view.OtherInfoUIState.Companion.URL_IMAGE_TIMES
import ayds.winchester.songinfo.moredetails.view.OtherInfoUIState.Companion.URL_IMAGE_WIKIPEDIA
import ayds.winchester.songinfo.utils.UtilsInjector
import ayds.winchester.songinfo.utils.navigation.NavigationUtils

interface OtherInfoWindow {
    var uiState: OtherInfoUIState
    val uiEventObservableFullArticle : Observable<OtherInfoWindowEvent>
    fun openExternalLink(url: String)
}

private const val SOURCE = "source: "
private const val NO_RESULTS = "No results"

internal class OtherInfoWindowImpl : AppCompatActivity(),OtherInfoWindow {

    private var descriptionsTexts : MutableList<TextView> = mutableListOf()
    private var descriptionsImages : MutableList<ImageView> = mutableListOf()
    private var descriptionsSources : MutableList<TextView> = mutableListOf()
    private var descriptionsButtons : MutableList<Button> = mutableListOf()

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
                is CardArtistDescription -> updateArtistDescription(cards[i],i)
                EmptyCard -> updateArtistDescriptionNoResult(i)
            }
        }
    }

    private fun updateDescriptionInfo(cards: List<Card>) {
        updateUiState(cards)
        showUI(cards)
    }

    private fun updateArtistDescription(description: Card, numberCard : Int){
        uiState.description = artistDescriptionHelper.getTextArtistDescription(description, uiState.artistName)
        uiState.actionsEnabled[numberCard] = true
        when(description.source){
            Source.WIKIPEDIA -> uiState.urlWikipedia = description.infoUrl
            Source.NEWYORKTIMES -> uiState.urlNYTimes = description.infoUrl
            Source.LASTFM -> uiState.urlLastFM = description.infoUrl
        }
    }

    private fun updateArtistDescriptionNoResult(numberCard : Int){
        uiState.actionsEnabled[numberCard] = false
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

    private fun notifyFullArticleAction(source : Source) {
        when (source){
            Source.WIKIPEDIA -> onActionSubject.notify(OtherInfoWindowEvent.FullArticleWikipedia)
            Source.LASTFM -> onActionSubject.notify(OtherInfoWindowEvent.FullArticleLastFM)
            Source.NEWYORKTIMES -> onActionSubject.notify(OtherInfoWindowEvent.FullArticleNYTimes)
        }
    }

    private fun setViewFullArticleButtonOnClick(cards: List<Card>){
        for(i in cards.indices){
            descriptionsButtons[i].setOnClickListener {
                notifyFullArticleAction(cards[i].source)
            }
        }
    }

    override fun openExternalLink(url: String) {
       navigationUtils.openExternalUrl(this, url)
    }

    private fun showUI(cards: List<Card>){
        runOnUiThread {
            if (cards.isNotEmpty()) {
                showImage(cards)
                showDescription(cards)
                enableActions(cards)
                setViewFullArticleButtonOnClick(cards)
            }else{
                showUINoResults()
                disabledButtons()
            }
        }
    }

    private fun showUINoResults(){
        descriptionsTexts[0].text = NO_RESULTS
        Picasso.get().load(URL_IMAGE_NOT_FOUND).into(descriptionsImages[0])
    }

    private fun disabledButtons(){
        for(i in descriptionsButtons.indices){
            descriptionsButtons[i].isEnabled=false
            descriptionsButtons[i].isVisible=false
        }
    }

    private fun enableActions(cards: List<Card>) {
        var cont = 0
        for(i in cards.indices) {
            descriptionsButtons[i].isEnabled = uiState.actionsEnabled[i]
            cont++
        }

        for(i in cont until descriptionsButtons.size){
            descriptionsButtons[i].isVisible = false
        }
    }

    private fun showImage(cards: List<Card>){
        var imageUrl : String

        for(i in cards.indices) {
            imageUrl = when(cards[i].source){
                Source.WIKIPEDIA-> URL_IMAGE_WIKIPEDIA
                Source.LASTFM-> URL_IMAGE_LASTFM
                Source.NEWYORKTIMES-> URL_IMAGE_TIMES
                Source.NOSOURCE-> URL_IMAGE_NOT_FOUND
            }
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