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
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.google.gson.JsonElement
import retrofit2.Response
import java.io.IOException
import java.lang.StringBuilder

class OtherInfoWindow : AppCompatActivity() {
    private var textPane2: TextView? = null
    private var dataBase: DataBase = DataBase(this as Context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        textPane2 = findViewById(R.id.textPane2)
        open(intent.getStringExtra("artistName"))
    }

    fun getArtistInfo(artistName: String?) {

        Log.e("TAG", "artistName $artistName")
        Thread {

            var artistDescription = getArtistDescriptionFromInternalDataBase(artistName!!)

            if (artistDescription == null)
                artistDescription=getArtistDescriptionFromService(artistName)

            showUI(artistDescription!!)

        }.start()
    }

    private fun getArtistDescriptionFromInternalDataBase(artistName: String?): String?{
        var artistDescription = dataBase.getInfo(dataBase!!, artistName!!)
        if (artistDescription != null)
            artistDescription = "[*]$artistDescription"

         return artistDescription
    }

    private fun getArtistDescriptionFromService(artistName: String): String{
        var artistDescription="No Results"
        try {

            val query = wikipediaSearch(artistName)
            val pageid = getPageId(query)

            artistDescription=makeDescription(query,artistName)

            manageViewFullArticleButton(pageid)

        } catch (e1: IOException) {
            Log.e("TAG", "Error $e1")
            e1.printStackTrace()
        }
        return artistDescription
    }

    private fun wikipediaSearch(artistName: String) : JsonObject{
        val wikipediaAPI = createRetrofit().create(WikipediaAPI::class.java)
        val callResponse: Response<String> = wikipediaAPI.getArtistInfo(artistName).execute()
        val gson = Gson()
        val jobj = gson.fromJson(callResponse.body(), JsonObject::class.java)

        return jobj["query"].asJsonObject
    }

    private fun createRetrofit () : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://en.wikipedia.org/w/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    private fun getPageId(json: JsonObject) : JsonElement{
        return json["search"].asJsonArray[0].asJsonObject["pageid"]
    }

    private fun makeDescription(query: JsonObject, artistName: String): String {
        val snippet = getSnippet(query)
        var artistDescription = "No Results"

        if (snippet != null) {
            artistDescription=getArtistDescription(snippet,artistName)
            saveDescriptionInDataBase(artistName,artistDescription)
        }

        return artistDescription
    }

    private fun getSnippet(json: JsonObject) : JsonElement{
        return json["search"].asJsonArray[0].asJsonObject["snippet"]
    }

    private fun getArtistDescription(snippet: JsonElement, artistName: String) : String{
        var artistDescription = snippet.asString.replace("\\n", "\n")
        artistDescription = textToHtml(artistDescription, artistName)

        return artistDescription
    }

    private fun saveDescriptionInDataBase(artistName: String,artistDescription: String){
        dataBase.saveArtist(artistName, artistDescription)
    }

    private fun manageViewFullArticleButton(pageid: JsonElement){
        val urlString = "https://en.wikipedia.org/?curid=$pageid"
        findViewById<View>(R.id.openUrlButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(urlString)
            startActivity(intent)
        }
    }

    private fun showUI(text: String){
        runOnUiThread {
            showImage()
            showDescription(text)
        }
    }

    private fun showImage(){
        val imageUrl = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"
        Picasso.get().load(imageUrl).into(findViewById<View>(R.id.imageView) as ImageView)
    }

    private fun showDescription(text: String){
        textPane2!!.text = Html.fromHtml(text)
    }

    private fun open(artist: String?) {
        dataBase = DataBase(this)
        dataBase.saveArtist( "test", "sarasa")

        getArtistInfo(artist)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }

    private fun textToHtml(text: String, term: String?): String {
        val builder = StringBuilder()
        builder.append("<html><div width=400>")
        builder.append("<font face=\"arial\">")
        val textWithBold = text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace("(?i)" + term!!.toRegex(), "<b>" + term.toUpperCase() + "</b>")
        builder.append(textWithBold)
        builder.append("</font></div></html>")
        return builder.toString()
    }
}