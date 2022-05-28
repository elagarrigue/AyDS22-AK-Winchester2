package ayds.winchester.songinfo.moredetails.view

data class OtherInfoUIState(
    val artistName : String = "",
    val description : String = "",
    val WikipediaUrl : String = "",
    val actionsEnabled: Boolean = false,
) {

    companion object {
        const val URL_ARTICLE = "https://en.wikipedia.org/?curid="
        const val URL_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"
    }
}