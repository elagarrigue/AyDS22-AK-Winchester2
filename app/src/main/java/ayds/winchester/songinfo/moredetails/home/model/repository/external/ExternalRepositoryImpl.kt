package ayds.winchester.songinfo.moredetails.home.model.repository.external

import ayds.winchester.songinfo.moredetails.home.model.entities.Description
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Response
import java.io.IOException

interface ExternalRepository{
    fun getArtistDescription(artistName: String): Description
}

internal class ExternalRepositoryImpl (private var wikipediaAPI : WikipediaAPI, private var wikipediaToDescriptionResolver : WikipediaToDescriptionResolver) : ExternalRepository{

    private lateinit var queryWikipediaSearch : Response<String>
    private lateinit var artistName : String

     override fun getArtistDescription(artistName: String): Description{
        this.artistName = artistName
        try {
            queryWikipediaSearch = wikipediaAPI.getArtistInfo(artistName).execute()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return wikipediaToDescriptionResolver.getDescriptionFromExternalData(queryWikipediaSearch, artistName)
    }
}