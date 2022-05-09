package ayds.winchester.songinfo.moredetails.home.model.repository.external

import ayds.winchester.songinfo.home.model.repository.external.spotify.tracks.JsonToSongResolver
import ayds.winchester.songinfo.home.model.repository.external.spotify.tracks.SpotifyToSongResolver
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val URL_RETROFIT = "https://en.wikipedia.org/w/"

object WikipediaInjector {
    private val wikipediaAPIRetrofit = Retrofit.Builder()
        .baseUrl(URL_RETROFIT)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
    private val wikipediaAPI = wikipediaAPIRetrofit.create(WikipediaAPI::class.java)
    private val wikipediaToDescriptionResolver: WikipediaToDescriptionResolver = WikipediaToDescriptionResolver()

    val wikipediaService: ExternalRepository = ExternalRepository(
        wikipediaAPI, wikipediaToDescriptionResolver
    )
}