package ayds.winchester.songinfo.moredetails.home.model

import android.content.Context
import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.moredetails.home.model.entities.ArtistDescription
import ayds.winchester.songinfo.moredetails.home.model.entities.Description
import ayds.winchester.songinfo.moredetails.home.model.repository.external.WikipediaAPI
import ayds.winchester.songinfo.moredetails.home.model.repository.local.DataBase

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import com.google.gson.Gson
import com.google.gson.JsonObject

import ayds.winchester.songinfo.moredetails.home.view.OtherInfoWindow
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
private const val PREFIX = "[*]"

class OtherInfoModel(otherInfoWindow: OtherInfoWindow){
    private lateinit var dataBase: DataBase
    private lateinit var artistName: String
    private lateinit var queryWikipediaSearch : JsonObject
    private var otherInfoView : OtherInfoWindow = otherInfoWindow
    private val onActionSubject = Subject<Description>()
    val uiEventObservable: Observable<Description> = onActionSubject

    init{
        initProperties()
    }

    private fun initProperties(){
        dataBase = DataBase(otherInfoView as Context)
    }

   // private fun notifySearchDescriptionAction() {
        //onActionSubject.notify(Description)
   // }

    private fun getArtistInfo() {
        Thread {
            getArtistInfoFromDataBaseOrService()
        }.start()
    }

    fun setNameArtist(name: String){
        artistName=name
        getArtistInfo()//----------------------Mover
    }

    private fun getArtistInfoFromDataBaseOrService():String{
        var artistDescription = getArtistDescriptionFromInternalDataBase()
        if (artistDescription == null) {
            artistDescription = getArtistDescriptionFromService()
            if (artistDescription != NO_RESULTS)
                saveDescriptionInDataBase(artistDescription)
        }
        onActionSubject.notify(ArtistDescription("123", artistDescription))
        return artistDescription
    }

    private fun getArtistDescriptionFromInternalDataBase(): String?{
        var artistDescription = dataBase.getInfo(dataBase, artistName)
        if (artistDescription != null)
            artistDescription = PREFIX +"$artistDescription"

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

    private fun createRetrofit () : Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL_RETROFIT)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    private fun getPageId(json: JsonObject) : JsonElement {
        return json[SEARCH].asJsonArray[0].asJsonObject[PAGE_ID]
    }

    private fun makeDescription(): String {
        val snippet = getSnippet(queryWikipediaSearch)
        return getArtistDescription(snippet)
    }

    private fun getSnippet(json: JsonObject) : JsonElement {
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