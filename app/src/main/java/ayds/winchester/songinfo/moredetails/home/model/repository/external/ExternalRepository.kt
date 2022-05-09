package ayds.winchester.songinfo.moredetails.home.model.repository.external

import ayds.winchester.songinfo.moredetails.home.model.entities.ArtistDescription
import ayds.winchester.songinfo.moredetails.home.model.entities.Description
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Response
import java.io.IOException
import java.lang.StringBuilder
import java.util.*

private const val NO_RESULTS = "No Results"
private const val SNIPPET = "snippet"
private const val SEARCH = "search"
private const val PAGE_ID = "pageid"
private const val QUERY = "query"

class ExternalRepository (wikipediaAPI: WikipediaAPI){

    private lateinit var queryWikipediaSearch : JsonObject
    private lateinit var artistName : String
    private var wikipediaAPI = wikipediaAPI

     fun getArtistDescription(artistName: String): Description{
        var artistDescription= NO_RESULTS
        this.artistName = artistName
         var pageId = ""
        try {
            queryWikipediaSearch = wikipediaSearch()
            artistDescription = makeDescription()
            pageId = getPageId(queryWikipediaSearch).asString
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return ArtistDescription(pageId, artistDescription)
    }

    private fun wikipediaSearch() : JsonObject {
        val callResponse: Response<String> = wikipediaAPI.getArtistInfo(artistName).execute()
        val gson = Gson()
        val jObj = gson.fromJson(callResponse.body(), JsonObject::class.java)

        return jObj[QUERY].asJsonObject
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

    private fun getPageId(json: JsonObject) : JsonElement {
        return json[SEARCH].asJsonArray[0].asJsonObject[PAGE_ID]
    }

}