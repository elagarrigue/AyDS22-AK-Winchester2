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
import ayds.winchester.songinfo.moredetails.model.entities.ArtistDescription
import ayds.winchester.songinfo.moredetails.model.entities.Description
import ayds.winchester.songinfo.moredetails.model.entities.EmptyDescription
import ayds.winchester.songinfo.moredetails.view.OtherInfoUIState.Companion.URL_ARTICLE
import ayds.winchester.songinfo.moredetails.view.OtherInfoUIState.Companion.URL_IMAGE
import ayds.winchester.songinfo.utils.UtilsInjector
import ayds.winchester.songinfo.utils.navigation.NavigationUtils

class OtherInfoWindow : AppCompatActivity() {
    private lateinit var descriptionPane: TextView
    private lateinit var wikipediaImage: ImageView
    private lateinit var pageId: String
    private lateinit var viewFullArticleButton : Button
    private val onActionSubject = Subject<OtherInfoWindowEvent>()
    val uiEventObservableFullArticle: Observable<OtherInfoWindowEvent> = onActionSubject
    private lateinit var otherInfoModel : OtherInfoModel
    private val navigationUtils: NavigationUtils = UtilsInjector.navigationUtils
    private val artistDescriptionHelper : ArtistDescriptionHelper = ArtistDescriptionHelperImpl()
    var uiState: OtherInfoUIState = OtherInfoUIState()

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

    private fun updateUiState(description: Description) {
        when (description) {
            is ArtistDescription -> updateArtistDescription(description)
            EmptyDescription -> updateArtistDescriptionNoResult()
        }
    }

    private fun updateDescriptionInfo(description: Description) {
        updateUiState(description)
        showUI(description)
    }

    private fun updateArtistDescription(description: Description){
        uiState = uiState.copy(
            description = artistDescriptionHelper.getTextArtistDescription(description, uiState.artistName),
            id = description.id,
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

    fun openExternalLink(id: String) {
        val urlString = URL_ARTICLE+id
        navigationUtils.openExternalUrl(this, urlString)
    }

    private fun showUI(description: Description){
        runOnUiThread {
            showImage()
            showDescription(description)
            pageId = uiState.id
            enableActions()
            setViewFullArticleButtonOnClick()
        }
    }

    private fun enableActions() {
        viewFullArticleButton.isEnabled = uiState.actionsEnabled
    }

    private fun showImage(){
        val imageUrl = URL_IMAGE
        Picasso.get().load(imageUrl).into(wikipediaImage)
    }

    private fun showDescription(description: Description){
        descriptionPane.text = Html.fromHtml(artistDescriptionHelper.getTextArtistDescription(description,uiState.artistName))
    }

    companion object {
        const val ARTIST_NAME = "artistName"
    }
}