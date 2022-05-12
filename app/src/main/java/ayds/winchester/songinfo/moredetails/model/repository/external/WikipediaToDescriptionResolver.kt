package ayds.winchester.songinfo.moredetails.model.repository.external

import ayds.winchester.songinfo.moredetails.model.entities.ArtistDescription
import ayds.winchester.songinfo.moredetails.model.entities.Description
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Response

private const val SNIPPET = "snippet"
private const val SEARCH = "search"
private const val PAGE_ID = "pageid"
private const val QUERY = "query"

internal class WikipediaToDescriptionResolver {

    private lateinit var queryWikipediaSearch : JsonObject
    private lateinit var artistName : String

    private fun makeDescription(): String {
        val snippet = getSnippet(queryWikipediaSearch)
        return getArtistDescription(snippet)
    }

    fun getDescriptionFromExternalData(queryWikipediaSearch : Response<String>, artistName: String): Description {

        val gson = Gson()
        val jObj = gson.fromJson(queryWikipediaSearch.body(), JsonObject::class.java)

        this.queryWikipediaSearch = jObj[QUERY].asJsonObject
        this.artistName = artistName

        val artistDescription = makeDescription()
        val pageId = getPageId(this.queryWikipediaSearch).asString

        return ArtistDescription(pageId,artistDescription)
    }

    private fun getSnippet(json: JsonObject) : JsonElement {
        return json[SEARCH].asJsonArray[0].asJsonObject[SNIPPET]
    }

    private fun getArtistDescription(snippet: JsonElement) : String{
        return snippet.asString.replace("\\n", "\n")
    }

    private fun getPageId(json: JsonObject) : JsonElement {
        return json[SEARCH].asJsonArray[0].asJsonObject[PAGE_ID]
    }

}