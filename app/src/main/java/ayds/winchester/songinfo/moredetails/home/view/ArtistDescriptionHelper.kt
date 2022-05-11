package ayds.winchester.songinfo.moredetails.home.view

import ayds.winchester.songinfo.moredetails.home.model.entities.Description
import java.lang.StringBuilder
import java.util.*

interface ArtistDescriptionHelper {
    fun getTextArtistDescription(description: Description, artistName: String): String
}

private const val PREFIX = "[*] "

internal class ArtistDescriptionHelperImpl() : ArtistDescriptionHelper {

    override fun getTextArtistDescription (description: Description, artistName: String) : String {
        val descriptionText = if(description.isLocallyStored) PREFIX + description.description else description.description
        return textToHtml(descriptionText, artistName)
    }
}

private fun textToHtml(descriptionText: String, artistName: String): String {
    val builder = StringBuilder()
    builder.append("<html><div width=400>")
    builder.append("<font face=\"arial\">")
    val textWithBold = descriptionText
        .replace("'", " ")
        .replace("\n", "<br>")
        .replace("(?i)" + artistName.toRegex(), "<b>" + artistName.uppercase(Locale.getDefault()) + "</b>")
    builder.append(textWithBold)
    builder.append("</font></div></html>")
    return builder.toString()
}
