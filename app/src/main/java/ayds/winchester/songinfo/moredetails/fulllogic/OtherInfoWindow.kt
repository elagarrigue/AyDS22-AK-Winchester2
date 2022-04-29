package ayds.winchester.songinfo.moredetails.fulllogic

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import ayds.winchester.songinfo.R
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import com.google.gson.Gson
import com.google.gson.JsonObject
import android.content.Intent
import android.net.Uri
import com.squareup.picasso.Picasso
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.google.gson.JsonElement
import retrofit2.Response
import java.io.IOException
import java.lang.StringBuilder
import java.util.*

private const val NO_RESULTS = "No Results"
private const val PAGE_ID = "pageid"
private const val SNIPPET = "snippet"
private const val SEARCH = "search"
private const val ARTIST_NAME = "artistName"
private const val QUERY = "query"
private const val URL_RETROFIT = "https://en.wikipedia.org/w/"
private const val URL_ARTICLE = "https://en.wikipedia.org/?curid="
private const val URL_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"
private const val PREFIX = "[*]"

class OtherInfoWindow : AppCompatActivity() {
    private lateinit var descriptionPane: TextView
    private lateinit var wikipediaImage: ImageView
    private lateinit var viewFullArticleButton : Button
    private var dataBase: DataBase = DataBase(this as Context)
    private lateinit var artistName: String
    private lateinit var queryWikipediaSearch : JsonObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        initProperties()
        initViewProperties()
        getArtistInfo()
    }

    private fun initProperties(){
        artistName = getArtistName()!!;
        dataBase = DataBase(this)
    }

    private fun initViewProperties(){
        descriptionPane = findViewById(R.id.textPaneArtistDescription)
        wikipediaImage = findViewById<View>(R.id.imageView) as ImageView
        viewFullArticleButton = findViewById(R.id.openUrlButton)
    }

    private fun getArtistInfo() {
        Thread {
            getArtistInfoFromDataBaseOrService()
        }.start()
    }

    private fun getArtistInfoFromDataBaseOrService(){
        var artistDescription = getArtistDescriptionFromInternalDataBase()
        if (artistDescription == null) {
            artistDescription = getArtistDescriptionFromService()
            if (artistDescription != NO_RESULTS)
                saveDescriptionInDataBase(artistDescription)
        }
        showUI(artistDescription)
    }

    private fun getArtistName():String?{
        return intent.getStringExtra(ARTIST_NAME)
    }

    private fun getArtistDescriptionFromInternalDataBase(): String?{
        var artistDescription = dataBase.getInfo(dataBase, artistName)
        if (artistDescription != null)
            artistDescription = PREFIX+"$artistDescription"

         return artistDescription
    }

    private fun getArtistDescriptionFromService(): String{
        var artistDescription= NO_RESULTS
        try {
            queryWikipediaSearch = wikipediaSearch()
            artistDescription = makeDescription()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return artistDescription
    }

    private fun wikipediaSearch() : JsonObject{
        val wikipediaAPI = createRetrofit().create(WikipediaAPI::class.java)
        val callResponse: Response<String> = wikipediaAPI.getArtistInfo(artistName).execute()
        val gson = Gson()
        val jObj = gson.fromJson(callResponse.body(), JsonObject::class.java)

        return jObj[QUERY].asJsonObject
    }

    private fun createRetrofit () : Retrofit{
        return Retrofit.Builder()
            .baseUrl(URL_RETROFIT)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    private fun getPageId(json: JsonObject) : JsonElement{
        return json[SEARCH].asJsonArray[0].asJsonObject[PAGE_ID]
    }

    private fun makeDescription(): String {
        val snippet = getSnippet(queryWikipediaSearch)
        return getArtistDescription(snippet)
    }

    private fun getSnippet(json: JsonObject) : JsonElement{
        return json[SEARCH].asJsonArray[0].asJsonObject[SNIPPET]
    }

    private fun getArtistDescription(snippet: JsonElement) : String{
        var artistDescription = snippet.asString.replace("\\n", "\n")
        artistDescription = textToHtml(artistDescription, artistName)
        return artistDescription
    }

    private fun saveDescriptionInDataBase(artistDescription: String){
        dataBase.saveArtist(artistName, artistDescription)
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

    private fun showUI(text: String){
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
        val pageId = getPageId(queryWikipediaSearch)
        manageViewFullArticleButton(pageId)
    }

    private fun showDescription(text: String){
        descriptionPane.text = Html.fromHtml(text)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = ARTIST_NAME
    }

    private fun textToHtml(text: String, term: String): String {
        val builder = StringBuilder()
        builder.append("<html><div width=400>")
        builder.append("<font face=\"arial\">")
        val textWithBold = text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace("(?i)" + term.toRegex(), "<b>" + term.uppercase(Locale.getDefault()) + "</b>")
        builder.append(textWithBold)
        builder.append("</font></div></html>")
        return builder.toString()
    }
}