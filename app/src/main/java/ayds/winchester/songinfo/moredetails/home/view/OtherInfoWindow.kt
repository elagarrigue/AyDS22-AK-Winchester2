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
import ayds.winchester.songinfo.utils.UtilsInjector
import ayds.winchester.songinfo.utils.navigation.NavigationUtils

private const val ARTIST_NAME = "artistName"
private const val URL_ARTICLE = "https://en.wikipedia.org/?curid="
private const val URL_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_other_info)
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
            .subscribe { value -> showUI(value) }
    }

    private fun getArtistName(): String {
        return intent.getStringExtra(ARTIST_NAME)!!
    }

    private fun notifySearchDescriptionAction() {
       onActionSubject.notify(MoreDetailsUiEventImpl(getArtistName()))
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

    fun openExternalLink(url: String) {
        val urlString = URL_ARTICLE+pageId
        navigationUtils.openExternalUrl(this, urlString)
    }

    private fun showUI(description: Description){
        runOnUiThread {
            showImage()
            showDescription(description)
            pageId = description.id
            setViewFullArticleButtonOnClick()
        }
    }

    private fun showImage(){
        val imageUrl = URL_IMAGE
        Picasso.get().load(imageUrl).into(wikipediaImage)
    }

    private fun showDescription(description: Description){
        descriptionPane.text = Html.fromHtml(artistDescriptionHelper.getTextArtistDescription(description,getArtistName()))
    }

    companion object {
        const val ARTIST_NAME_EXTRA = ARTIST_NAME
    }
}