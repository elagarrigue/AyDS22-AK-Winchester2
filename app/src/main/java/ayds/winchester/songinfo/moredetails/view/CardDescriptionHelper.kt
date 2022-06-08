package ayds.winchester.songinfo.moredetails.view

import ayds.winchester.songinfo.moredetails.model.entities.Card
import java.lang.StringBuilder
import java.util.*

interface CardDescriptionHelper {
    fun getTextCard(description: Card, artistName: String): String
}

private const val PREFIX = "[*] "
private const val HTML_OPEN = "<html>"
private const val DIV_OPEN = "<div width=400>"
private const val FONT_OPEN = "<font face=\"arial\">"
private const val FONT_CLOSE = "</font>"
private const val DIV_CLOSE = "</div>"
private const val HTML_CLOSE = "</html>"
private const val BOLD_OPEN = "<b>"
private const val BOLD_CLOSE = "</b>"
private const val BREAK = "<br>"
private const val ENTER = "\n"
private const val SPACE = " "
private const val QUOTE = "'"
private const val ITALIC = "'"

internal class CardDescriptionHelperImpl : CardDescriptionHelper {

    override fun getTextCard(description: Card, artistName: String): String {
        val descriptionText =
            if (description.isLocallyStored) PREFIX + description.description else description.description
        return textToHtml(descriptionText, artistName)
    }
}

private fun textToHtml(descriptionText: String, artistName: String): String {
    val builder = StringBuilder()
    builder.append(HTML_OPEN + DIV_OPEN)
    builder.append(FONT_OPEN)
    val textWithBold = descriptionText
        .replace(QUOTE, SPACE)
        .replace(ENTER, BREAK)
        .replace(
            ITALIC + artistName.toRegex(),
            BOLD_OPEN + artistName.uppercase(Locale.getDefault()) + BOLD_CLOSE
        )
    builder.append(textWithBold)
    builder.append(FONT_CLOSE + DIV_CLOSE + HTML_CLOSE)
    return builder.toString()
}
