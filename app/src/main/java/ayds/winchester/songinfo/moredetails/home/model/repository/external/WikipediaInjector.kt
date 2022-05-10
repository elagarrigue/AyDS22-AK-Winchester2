package ayds.winchester.songinfo.moredetails.home.model.repository.external

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

    val wikipediaService: ExternalRepositoryImpl = ExternalRepositoryImpl(
        wikipediaAPI, wikipediaToDescriptionResolver
    )
}