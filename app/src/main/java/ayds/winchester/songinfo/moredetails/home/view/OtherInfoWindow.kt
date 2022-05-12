package ayds.winchester.songinfo.moredetails.home.view

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
import ayds.winchester.songinfo.moredetails.home.model.OtherInfoModelInjector
import ayds.winchester.songinfo.moredetails.home.model.OtherInfoModel
import ayds.winchester.songinfo.moredetails.home.model.entities.Description
import ayds.winchester.songinfo.moredetails.home.view.OtherInfoUIState.Companion.URL_ARTICLE
import ayds.winchester.songinfo.moredetails.home.view.OtherInfoUIState.Companion.URL_IMAGE
import ayds.winchester.songinfo.utils.UtilsInjector
import ayds.winchester.songinfo.utils.navigation.NavigationUtils

private const val ARTIST_NAME = "artistName"

class OtherInfoWindow : AppCompatActivity() {
    private lateinit var descriptionPane: TextView
    private lateinit var wikipediaImage: ImageView
    private lateinit var pageId: String
    private lateinit var viewFullArticleButton : Button
    private val onActionSubject = Subject<MoreDetailsUiEvent>()
    private val onActionSubjectFullArticle = Subject<OtherInfoWindowEvent>()
    val uiEventObservable: Observable<MoreDetailsUiEvent> = onActionSubject
    val uiEventObservableFullArticle: Observable<OtherInfoWindowEvent> = onActionSubjectFullArticle
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

    private fun updateDescriptionInfo(description: Description) {
        updateArtistDescription(description)
        showUI(description)
    }

    private fun updateArtistDescription(description: Description){
        uiState = uiState.copy(
            description = artistDescriptionHelper.getTextArtistDescription(description, uiState.artistName),
            id = description.id
        )
    }

    private fun updateStateArtistName() {
        uiState = uiState.copy(
            artistName = intent.getStringExtra(ARTIST_NAME)!!,
        )
    }

    private fun notifySearchDescriptionAction() {
       onActionSubject.notify(MoreDetailsUiEventImpl(uiState.artistName))
    }

    private fun initViewProperties(){
        descriptionPane = findViewById(R.id.textPaneArtistDescription)
        wikipediaImage = findViewById<View>(R.id.imageView) as ImageView
        viewFullArticleButton = findViewById(R.id.openUrlButton)
    }

    private fun notifyFullArticleAction() {
        onActionSubjectFullArticle.notify(OtherInfoWindowEvent.fullArticle)
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
            setViewFullArticleButtonOnClick()
        }
    }

    private fun showImage(){
        val imageUrl = URL_IMAGE
        Picasso.get().load(imageUrl).into(wikipediaImage)
    }

    private fun showDescription(description: Description){
        descriptionPane.text = Html.fromHtml(artistDescriptionHelper.getTextArtistDescription(description,uiState.artistName))
    }

    companion object {
        const val ARTIST_NAME_EXTRA = ARTIST_NAME
    }
}