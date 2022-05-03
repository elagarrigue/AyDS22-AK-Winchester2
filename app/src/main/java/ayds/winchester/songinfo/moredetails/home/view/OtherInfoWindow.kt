package ayds.winchester.songinfo.moredetails.home.view

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import ayds.winchester.songinfo.R
import android.content.Intent
import android.net.Uri
import com.squareup.picasso.Picasso
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ImageView
import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.moredetails.home.controller.OtherInfoControllerImpl
import com.google.gson.JsonElement

private const val ARTIST_NAME = "artistName"
private const val URL_ARTICLE = "https://en.wikipedia.org/?curid="
private const val URL_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"

class OtherInfoWindow : AppCompatActivity() {
    private lateinit var descriptionPane: TextView
    private lateinit var wikipediaImage: ImageView
    private lateinit var viewFullArticleButton : Button
    private var controller = OtherInfoControllerImpl()
    private val onActionSubject = Subject<MoreDetailsUiEvent>()
    val uiEventObservable: Observable<MoreDetailsUiEvent> = onActionSubject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        controller.setView(this)
        initViewProperties()
        notifySearchDescriptionAction()
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

    private fun manageViewFullArticleButton(pageId: JsonElement){
        val urlString = URL_ARTICLE+"$pageId"
        viewFullArticleButton.setOnClickListener {
            startViewFullArticleButton(urlString)
        }
    }

    private fun startViewFullArticleButton(urlString : String){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(urlString)
        startActivity(intent)
    }

    fun showUI(text: String){
        runOnUiThread {
            showImage()
            showDescription(text)
            showButton()
        }
    }

    private fun showImage(){
        val imageUrl = URL_IMAGE
        Picasso.get().load(imageUrl).into(wikipediaImage)
    }

    private fun showButton(){
        //val pageId = getPageId(queryWikipediaSearch)
        //manageViewFullArticleButton(pageId)
    }

    private fun showDescription(text: String){
        descriptionPane.text = Html.fromHtml(text)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = ARTIST_NAME
    }


}