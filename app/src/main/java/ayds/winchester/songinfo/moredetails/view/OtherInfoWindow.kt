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
import ayds.winchester.songinfo.moredetails.view.OtherInfoUIState.Companion.URL_IMAGE
import ayds.winchester.songinfo.utils.UtilsInjector
import ayds.winchester.songinfo.utils.navigation.NavigationUtils

interface OtherInfoWindow {
    var uiState: OtherInfoUIState
    val uiEventObservableFullArticle : Observable<OtherInfoWindowEvent>
    fun openExternalLink(url: String)
}

private const val SOURCE="source: "

internal class OtherInfoWindowImpl : AppCompatActivity(),OtherInfoWindow {
    private lateinit var descriptionPane: TextView
    private lateinit var sourcePane: TextView
    private lateinit var wikipediaImage: ImageView
    private lateinit var pageId: String
    private lateinit var viewFullArticleButton : Button
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

    private fun updateUiState(description: Card) {
        when (description) {
            is CardArtistDescription -> updateArtistDescription(description)
            EmptyCard -> updateArtistDescriptionNoResult()
        }
    }

    private fun updateDescriptionInfo(card: Card) {
        updateUiState(card)
        showUI(card)
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
        descriptionPane = findViewById(R.id.textPaneArtistDescription)
        sourcePane = findViewById(R.id.sourceText)
        wikipediaImage = findViewById<View>(R.id.imageView) as ImageView
        viewFullArticleButton = findViewById(R.id.openUrlButton)
    }

    private fun notifyFullArticleAction() {
        onActionSubject.notify(OtherInfoWindowEvent.FullArticle)
    }

    private fun setViewFullArticleButtonOnClick(){
        viewFullArticleButton.setOnClickListener {
           notifyFullArticleAction()
        }
    }

    override fun openExternalLink(url: String) {
       navigationUtils.openExternalUrl(this, url)
    }

    private fun showUI(description: Card){
        runOnUiThread {
            showImage()
            showDescription(description)
            pageId = uiState.WikipediaUrl
            enableActions()
            setViewFullArticleButtonOnClick()
        }
    }

    private fun enableActions() {
        viewFullArticleButton.isEnabled = uiState.actionsEnabled
    }

    private fun showImage(){
        Picasso.get().load(URL_IMAGE).into(wikipediaImage)
    }

    private fun showDescription(description: Card){
        descriptionPane.text = Html.fromHtml(artistDescriptionHelper.getTextArtistDescription(description,uiState.artistName))
        sourcePane.text= SOURCE+description.source
    }

    companion object {
        const val ARTIST_NAME = "artistName"
    }
}